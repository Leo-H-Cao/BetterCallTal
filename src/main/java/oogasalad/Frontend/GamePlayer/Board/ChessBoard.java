package oogasalad.Frontend.GamePlayer.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import oogasalad.Frontend.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.Frontend.GamePlayer.EngineExceptions.EngineException;
import oogasalad.Frontend.GamePlayer.EngineExceptions.MoveAfterGameEndException;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.Frontend.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChessBoard implements Iterable<ChessTile> {

  private static final Logger LOG = LogManager.getLogger(ChessBoard.class);

  private List<List<ChessTile>> board;
  private TurnCriteria turnCriteria;
  private Player[] players;
  private List<EndCondition> endConditions;
  private int currentPlayer;
  private Map<Integer, Double> endResult;
  private List<ChessBoard> history;

  /***
   * Creates a representation of a chessboard if an array of pieces is already provided
   */
  public ChessBoard(List<List<ChessTile>> board, TurnCriteria turnCriteria, Player[] players, List<EndCondition> endConditions) {
    this.board = board;
    this.turnCriteria = turnCriteria;
    this.players = players;
    this.endConditions = endConditions;
    currentPlayer = turnCriteria.getCurrentPlayer();
    endResult = new HashMap<>();
    history = new ArrayList<>();
  }
  
  /***
   * Creates a representation of a chessboard with length/height of board given
   */
  public ChessBoard(int length, int height, TurnCriteria turnCriteria, Player[] players, List<EndCondition> endConditions) {
    this(null, turnCriteria, players, endConditions);

    board = new ArrayList<>();
    IntStream.range(0, height)
        .forEach(i -> {
          List<ChessTile> list = new ArrayList<>();
          IntStream.range(0, length)
              .forEach(j -> list.add(new ChessTile(new Coordinate(i, j))));
          board.add(list);
        });
  }

  /***
   * Sets the pieces on the chess board if at starting position (i.e. history is empty)
   *
   * @param pieces to add to the board
   * @return if the pieces are set
   */
  public boolean setPieces(List<Piece> pieces) {
    if(history.isEmpty()) {

      pieces.forEach(p -> {
        Coordinate coordinate = p.getCoordinates();
        board.get(coordinate.getRow()).get(coordinate.getCol()).addPiece(p);
      });

      history.add(deepCopy());
      return true;
    }
    return false;
  }

  /***
   * Moves the piece to the finalSquare
   *
   * @param piece to move
   * @param finalSquare end square
   * @return set of updated tiles + next player turn
   */
  public TurnUpdate move(Piece piece, Coordinate finalSquare) throws EngineException {
    if(!isGameOver() && piece.checkTeam(turnCriteria.getCurrentPlayer())) {
      history.add(deepCopy());
      return new TurnUpdate(piece.move(getTileFromCoords(finalSquare)), turnCriteria.incrementTurn());
    }
    throw isGameOver() ? new MoveAfterGameEndException("") : new WrongPlayerException(turnCriteria.getCurrentPlayer() + "");
  }

  /**
   * This method gets the target pieces for the specified team
   * @param team the Team we want information from
   * @return all the Target Pieces this team has
   */
  public List<Piece> targetPiece(int team) {
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> piece.checkTeam(team) && piece.isTargetPiece())
        .collect(Collectors.toList());
  }
  /***
   * @return copy of Board object to store in history
   */
  public ChessBoard deepCopy() {
    //TODO: CLONE PIECES AS WELL
    return new ChessBoard(this.board, this.turnCriteria, this.players, this.endConditions);
  }

  /***
   * @return list of board history
   */
  public List<ChessBoard> getHistory() {
    return history;
  }

  /***
   * @param coordinates to get in board
   * @return corresponding tile in board
   */
  private ChessTile getTileFromCoords(Coordinate coordinates) {
    return board.get(coordinates.getRow()).get(coordinates.getCol());
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece) {
    return piece.getMoves();
  }

  /***
   * @return if the game is over
   */
  public boolean isGameOver() {
    for(EndCondition ec: endConditions) {
      Optional<Map<Integer, Double>> endResultOptional = ec.getScores(this);
      if(endResultOptional.isPresent()) {
        endResult = endResultOptional.get();
        return true;
      }
    }
    return false;
  }

  /***
   * @return scores of all teams after game over. If game isn't over, an empty optional is returned.
   */
  public Optional<Map<Integer, Double>> getScores() {
    return endResult != null && !endResult.isEmpty() ? Optional.of(endResult) : Optional.empty();
  }

  /***
   * @param coordinates to check
   * @return if a given coordinate is in bounds
   */
  public boolean inBounds(Coordinate coordinates) {
    return coordinates.getRow() >= 0 && coordinates.getCol() >= 0 && coordinates.getRow() < board.size()
      && coordinates.getCol() < board.get(coordinates.getRow()).size();
  }

  /***
   * Gets the tile at the specified coordinates
   *
   * @param coordinate is the coordinate of the tile to get
   * @return tile at specified coordinate
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public ChessTile getTile(Coordinate coordinate) throws OutsideOfBoardException {
    if(!inBounds(coordinate)) throw new OutsideOfBoardException(coordinate.toString());
    return board.get(coordinate.getRow()).get(coordinate.getCol());
  }

  /***
   * Returns if a tile is empty
   *
   * @param coordinate to check
   * @return if the tile is empty
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public boolean isTileEmpty(Coordinate coordinate) throws OutsideOfBoardException {
    if(!inBounds(coordinate)) throw new OutsideOfBoardException(coordinate.toString());
    return getTile(coordinate).getPiece().isEmpty();
  }

  /***
   * @param id of player
   * @return player with given id
   */
  public Player getPlayer(int id) {
    return players[Math.min(id, players.length - 1)];
  }

  /***
   * @return players list
   */
  public Player[] getPlayers() {
    return players;
  }

  /**
   * @return The length of the board
   */
  public int getBoardLength(){
    return board.get(0).size();
  }

  /**
   * @return The height of the board
   */
  public int getBoardHeight(){
    return board.size();
  }

  /**
   *
   * @param pieceLocation
   * @param piece
   */
  public void placePiece(Coordinate pieceLocation, Piece piece) {
    this.board.get(pieceLocation.getRow()).get(pieceLocation.getCol()).addPiece(piece);
  }

  @Override
  public Iterator<ChessTile> iterator() {
    return new ChessBoardIterator(board);
  }

  public Stream<List<ChessTile>> stream() {
    return board.stream();
  }

  @Override
  public void forEach(Consumer<? super ChessTile> action) {
    Iterable.super.forEach(action);
  }

  private class ChessBoardIterator implements Iterator<ChessTile> {

    private final Queue<ChessTile> queue;

    public ChessBoardIterator(List<List<ChessTile>> board) {
      queue = new LinkedList<>();
      board.forEach(queue::addAll);
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    @Override
    public ChessTile next() {
      return queue.poll();
    }
  }
}
