package oogasalad.GamePlayer.Board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.MoveAfterGameEndException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Server.ChessHistory;
import oogasalad.GamePlayer.Server.LocalTurnManager;
import oogasalad.GamePlayer.Server.TurnManager;
import oogasalad.GamePlayer.ValidStateChecker.Check;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChessBoard implements Iterable<ChessTile> {

  private static final Logger LOG = LogManager.getLogger(ChessBoard.class);
  private final GamePlayers players;
  private final List<ValidStateChecker> validStateCheckers;
  private final TurnManager turnManager;
  private List<List<ChessTile>> board;

  /**
   * Creates a representation of a chessboard if an array of pieces is already provided
   */
  public ChessBoard(List<List<ChessTile>> board, TurnCriteria turnCriteria, Player[] players,
      List<ValidStateChecker> validStateCheckers, List<EndCondition> endConditions) {
    this.players = new GamePlayers(players);
    this.turnManager = new LocalTurnManager(new ChessHistory(), this.players, turnCriteria,
        endConditions);
    this.board = board;
    this.validStateCheckers = validStateCheckers;
  }

  public ChessBoard(List<List<ChessTile>> board, TurnManager turnManager, GamePlayers players,
      List<ValidStateChecker> validStateCheckers) {
    this.players = players;
    this.turnManager = turnManager;
    this.board = board;
    this.validStateCheckers = validStateCheckers;
  }

  /**
   * Creates a representation of a chessboard with length/height of board given but no valid state
   * checkers given
   */
  public ChessBoard(int length, int height, TurnCriteria turnCriteria, Player[] players,
      List<EndCondition> endConditions) {
    this(length, height, turnCriteria, players, List.of(), endConditions);
  }

  /**
   * Creates a representation of a chessboard with length/height of board given
   */
  public ChessBoard(int length, int height, TurnCriteria turnCriteria, Player[] players,
      List<ValidStateChecker> validStateCheckers, List<EndCondition> endConditions) {
    this(null, turnCriteria, players, validStateCheckers, endConditions);
    board = new ArrayList<>();
    IntStream.range(0, height).forEach(i -> {
      List<ChessTile> list = new ArrayList<>();
      IntStream.range(0, length).forEach(j -> list.add(new ChessTile(new Coordinate(i, j))));
      board.add(list);
    });
  }

  /**
   * Sets the pieces on the chess board if at starting position (i.e. history is empty)
   *
   * @param pieces to add to the board
   * @return if the pieces are set
   */
  public boolean setPieces(List<Piece> pieces) {
    if (turnManager.isHistoryEmpty()) {
      pieces.forEach(p -> {
        Coordinate coordinate = p.getCoordinates();
        try {
          getTile(coordinate).addPiece(p);
        } catch (OutsideOfBoardException ignored) {
          LOG.warn("Set pieces has out of bounds coordinate");
        }
      });
      ChessBoard copied = deepCopy();
      LOG.debug("History updated for first time");
      turnManager.addToHistory(new History(copied, new HashSet<>(pieces), pieces.stream()
          .map(p -> board.get(p.getCoordinates().getRow()).get(p.getCoordinates().getCol()))
          .collect(Collectors.toSet())));
      return true;
    }
    LOG.warn("Attempted board setting after game start");
    return false;
  }

  /**
   * Moves the piece to the finalSquare
   *
   * @param piece       to move
   * @param finalSquare end square
   * @return set of updated tiles + next player turn
   */
  public TurnUpdate move(Piece piece, Coordinate finalSquare) throws EngineException {
    // TODO: valid state checker for person who just moved (redundunt - optional)
    // TODO: check end conditions for other player(s)
    if (!isGameOver() && piece.checkTeam(turnManager.getCurrentPlayer())) {
      TurnUpdate update = new TurnUpdate(piece.move(getTileFromCoords(finalSquare), this),
          turnManager.incrementTurn());
      turnManager.addToHistory(new History(deepCopy(), Set.of(piece), update.updatedSquares()));
      LOG.debug("History updated: " + turnManager.getHistorySize());
      return update;
    }
    LOG.warn(isGameOver() ? "Move made after game over" : "Move made by wrong player");
    throw isGameOver() ? new MoveAfterGameEndException("") : new WrongPlayerException(
        "Expected: " + turnManager.getCurrentPlayer() + "\n Actual: " + piece.getTeam());
  }

  /**
   * Copies this board and then makes the move
   *
   * @param piece       to move
   * @param finalSquare to move the piece to
   * @return copy of the chessboard after the hypothetical move is made
   */
  public ChessBoard makeHypotheticalMove(Piece piece, Coordinate finalSquare)
      throws EngineException {
    ChessBoard boardCopy = deepCopy();
    Piece copiedPiece = boardCopy.getTile(piece.getCoordinates()).getPiece()
        .orElseThrow(() -> new InvalidMoveException("Hypothetical move could not be made"));
    copiedPiece.move(boardCopy.getTile(finalSquare), boardCopy);

    return boardCopy;
  }

  /**
   * This method gets the target pieces for the specified team
   *
   * @param team the Team we want information from
   * @return all the Target Pieces this team has
   */
  public List<Piece> targetPiece(int team) {
    return board.stream().flatMap(List::stream).toList().stream().map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> piece.checkTeam(team) && piece.isTargetPiece()).toList();
  }

  /**
   * @return copy of Board object to store in history
   */
  public ChessBoard deepCopy() {
    List<List<ChessTile>> boardCopy = new ArrayList<>();
    IntStream.range(0, this.board.size()).forEach(i -> {
      boardCopy.add(new ArrayList<>());
      boardCopy.get(i).addAll(this.board.get(i).stream().map(ChessTile::clone).toList());
    });
    return new ChessBoard(boardCopy, this.turnManager, this.players, this.validStateCheckers);
  }

  /**
   * @param coordinates to get in board
   * @return corresponding tile in board
   */
  private ChessTile getTileFromCoords(Coordinate coordinates) {
    return board.get(coordinates.getRow()).get(coordinates.getCol());
  }

  /**
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece) throws EngineException {
    // TODO: add valid state checker here
    ValidStateChecker check = new Check();
    Set<ChessTile> allPieceMovements = piece.getMoves(this);
    allPieceMovements.removeIf(entry -> {
      ChessBoard copy;
      try {
        // TODO: warning optional.get() without checking isPresent()
        copy = makeHypotheticalMove(this.getTile(piece.getCoordinates()).getPiece().get(),
            entry.getCoordinates());
        if (!check.isValid(copy, piece.getTeam())) {
          return true;
        }
      } catch (EngineException e) {
        return false;
      }
      return false;
    });
    return piece.checkTeam(turnManager.getCurrentPlayer()) ? allPieceMovements : Set.of();
  }

  /**
   * @return if the game is over
   */
  public boolean isGameOver() {
    return turnManager.isGameOver(this);
  }

  /**
   * @return scores of all teams after game over. If game isn't over, an empty optional is returned.
   */
  public Map<Integer, Double> getScores() {
    return turnManager.getScores();
  }

  /**
   * @param coordinates to check
   * @return if a given coordinate is in bounds
   */
  public boolean inBounds(Coordinate coordinates) {
    return coordinates.getRow() >= 0 && coordinates.getCol() >= 0
        && coordinates.getRow() < board.size() && coordinates.getCol() < board.get(
        coordinates.getRow()).size();
  }

  /**
   * Gets the tile at the specified coordinates
   *
   * @param coordinate is the coordinate of the tile to get
   * @return tile at specified coordinate
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public ChessTile getTile(Coordinate coordinate) throws OutsideOfBoardException {
    if (!inBounds(coordinate)) {
      throw new OutsideOfBoardException(coordinate.toString());
    }
    return board.get(coordinate.getRow()).get(coordinate.getCol());
  }

  /**
   * Returns if a tile is empty
   *
   * @param coordinate to check
   * @return if the tile is empty
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public boolean isTileEmpty(Coordinate coordinate) throws OutsideOfBoardException {
    if (!inBounds(coordinate)) {
      throw new OutsideOfBoardException(coordinate.toString());
    }
    return getTile(coordinate).getPiece().isEmpty();
  }

  /**
   * Gets the player object with the associated ID
   *
   * @param id of player
   * @return player with given id
   */
  public Player getPlayer(int id) {
    return players.getPlayer(id);
  }

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  public Player[] getPlayers() {
    return players.getPlayers();
  }

  /**
   * Gets all team numbers
   *
   * @return team numbers for all players
   */
  public int[] getTeams() {
    return players.getTeams();
  }

  /**
   * @return The length of the board
   */
  public int getBoardLength() {
    return board.get(0).size();
  }

  /**
   * @return The height of the board
   */
  public int getBoardHeight() {
    return board.size();
  }

  /**
   * Places piece at designated spot
   *
   * @param pieceLocation to put piece
   * @param piece         to place
   */
  public void placePiece(Coordinate pieceLocation, Piece piece) {
    this.board.get(pieceLocation.getRow()).get(pieceLocation.getCol()).addPiece(piece);
  }

  /**
   * @return iterator over the board list
   */
  @Override
  public Iterator<ChessTile> iterator() {
    return new ChessBoardIterator(board);
  }

  /**
   * @return stream over the board
   */
  public Stream<List<ChessTile>> stream() {
    return board.stream();
  }

  /**
   * Gets all the pieces on the board
   */
  public List<Piece> getPieces() {
    return board.stream().flatMap(List::stream).toList().stream().map(ChessTile::getPieces)
        .flatMap(List::stream).toList();
  }

  /**
   * Creates foreach loop over board
   *
   * @param action to do in loop
   */
  @Override
  public void forEach(Consumer<? super ChessTile> action) {
    Iterable.super.forEach(action);
  }

  /**
   * Iterator class over the board list
   */
  private static class ChessBoardIterator implements Iterator<ChessTile> {

    private final Queue<ChessTile> queue;

    /**
     * Creates an iterator over a given list
     */
    public ChessBoardIterator(List<List<ChessTile>> board) {
      queue = new LinkedList<>();
      board.forEach(queue::addAll);
    }

    /**
     * @return if there's another ChessTile
     */
    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    /**
     * @return next ChessTile
     */
    @Override
    public ChessTile next() throws NoSuchElementException {
      return queue.poll();
    }
  }
}