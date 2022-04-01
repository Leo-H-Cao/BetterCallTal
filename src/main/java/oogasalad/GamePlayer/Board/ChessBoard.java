package oogasalad.GamePlayer.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.MoveAfterGameEndException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class ChessBoard {

  private ChessTile[][] board;
  private TurnCriteria turnCriteria;
  private Player[] players;
  private List<EndCondition> endConditions;
  private int currentPlayer;
  private Map<Integer, Double> endResult;
  private List<ChessBoard> history;

  /***
   * Creates a representation of a chessboard if an array of pieces is already provided
   */
  public ChessBoard(ChessTile[][] board, TurnCriteria turnCriteria, Player[] players, List<EndCondition> endConditions) {
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
    board = new ChessTile[length][height];
    IntStream.range(0, board.length).forEach((i) -> IntStream.range(0, board[i].length).forEach((j) -> board[i][j] = new ChessTile(new Coordinate(i, j))));
  }

  /***
   * Sets the pieces on the chess board if at starting position (i.e. history is empty)
   *
   * @param pieces to add to the board
   * @return if the pieces are set
   */
  public boolean setPieces(List<Piece> pieces) {
    if(history.isEmpty()) {
      pieces.forEach(
          (p) -> board[p.getCoordinates().getRow()][p.getCoordinates().getCol()].addPiece(p));
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

  /***
   * @return copy of Board object to store in history
   */
  private ChessBoard deepCopy() {
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
    return board[coordinates.getRow()][coordinates.getCol()];
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
  public boolean inBounds(Coordinate coordinates) {return coordinates.getRow() >= 0 && coordinates.getCol() >= 0 && coordinates.getRow() < board.length && coordinates.getCol() < board[coordinates.getRow()].length;}

  /***
   * Gets the tile at the specified coordinates
   *
   * @param coordinate is the coordinate of the tile to get
   * @return tile at specificed coordinate
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public ChessTile getTile(Coordinate coordinate) throws OutsideOfBoardException {
    if(!inBounds(coordinate)) throw new OutsideOfBoardException(coordinate.toString());
    return board[coordinate.getRow()][coordinate.getCol()];
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
}
