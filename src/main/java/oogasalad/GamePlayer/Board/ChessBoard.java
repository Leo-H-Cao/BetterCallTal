package oogasalad.GamePlayer.Board;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.History.History;
import oogasalad.GamePlayer.Board.History.HistoryManager;
import oogasalad.GamePlayer.Board.History.HistoryManagerData;
import oogasalad.GamePlayer.Board.History.LocalHistoryManager;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;
import oogasalad.GamePlayer.Board.TurnManagement.LocalTurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManagerData;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.MoveAfterGameEndException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class representing the chess board
 *
 * @author Jed, Jose, Vincent, Ritvik
 */
public class ChessBoard implements Iterable<ChessTile> {

  private static final Logger LOG = LogManager.getLogger(ChessBoard.class);
  private final GamePlayers players;
  private final List<ValidStateChecker> validStateCheckers;
  private final TurnManager turnManager;
  private final HistoryManager history;
  private final TurnManagerData turnManagerData;
  private List<List<ChessTile>> board;
  private Map<Integer, List<Piece>> pieceList;
  private Consumer<Throwable> showAsyncError = LOG::error;
  private Consumer<TurnUpdate> performAsyncTurnUpdate = LOG::info;

  /**
   * Creates a representation of a chessboard if an array of pieces is already provided
   */
  public ChessBoard(List<List<ChessTile>> board, TurnCriteria turnCriteria, Player[] players,
      List<ValidStateChecker> validStateCheckers, List<EndCondition> endConditions) {
    this.players = new GamePlayers(players);
    this.turnManagerData = new TurnManagerData(this.players, turnCriteria, endConditions, "");
    this.turnManager = new LocalTurnManager(this.turnManagerData);
    this.board = board;
    this.validStateCheckers = validStateCheckers;
    this.history = new LocalHistoryManager();
    this.pieceList = new HashMap<>();
  }

  public ChessBoard(List<List<ChessTile>> board, TurnManagerData turnManagerData,
      GamePlayers players,
      List<ValidStateChecker> validStateCheckers, HistoryManager history) {
    this.players = players;
    this.turnManagerData = turnManagerData;
    this.turnManager = new LocalTurnManager(this.turnManagerData);
    this.board = board;
    this.validStateCheckers = validStateCheckers;
    this.history = history;
    this.pieceList = new HashMap<>();
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

  public ChessBoard(ChessBoardData boardData) {
    this.players = boardData.players();
    this.turnManagerData = boardData.turnManagerData();
    this.turnManager = new LocalTurnManager(this.turnManagerData);
    this.board = boardData.board();
    this.validStateCheckers = boardData.validStateCheckers();
    this.history = new LocalHistoryManager(boardData.history());
  }

  /**
   * Gets the data required to set up a new turn manager
   *
   * @return the data required to set up a new turn manager
   */
  public TurnManagerData getTurnManagerData() {
    return turnManagerData;
  }

  /**
   * Generates the list of all pieces mapped to each team
   */
  private void generatePieceList() {
    board.forEach(l -> l.stream().filter(t -> t.getPiece().isPresent()).forEach(t -> {
      Piece piece = t.getPiece().get();
      pieceList.putIfAbsent(piece.getTeam(), new ArrayList<>());
      if (pieceList.get(piece.getTeam()).stream().noneMatch(p ->
          p.getName().equals(piece.getName()))) {
        pieceList.get(piece.getTeam()).add(piece.clone());
      }
    }));
    LOG.debug(String.format("Piece list generated: %s", pieceList));
  }

  /**
   * Sets the pieces on the chess board if at starting position (i.e. history is empty)
   *
   * @param pieces to add to the board
   * @return if the pieces are set
   */
  public boolean setPieces(List<Piece> pieces) {
    if (history.isEmpty()) {
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
      history.add(new History(copied, new HashSet<>(pieces), pieces.stream().filter(p ->
              this.inBounds(p.getCoordinates()))
          .map(p -> board.get(p.getCoordinates().getRow()).get(p.getCoordinates().getCol()))
          .collect(Collectors.toSet())));
      generatePieceList();
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
      history.add(new History(deepCopy(), Set.of(piece), update.updatedSquares()));
      LOG.debug(String.format("History updated: %d", history.size()));
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
   * Makes a deep copy of the board
   *
   * @return copy of Board object to store in history
   */
  public ChessBoard deepCopy() {
    List<List<ChessTile>> boardCopy = new ArrayList<>();
    IntStream.range(0, this.board.size()).forEach(i -> {
      boardCopy.add(new ArrayList<>());
      boardCopy.get(i).addAll(this.board.get(i).stream().map(ChessTile::clone).toList());
    });
    return new ChessBoard(boardCopy, this.turnManagerData.copy(), this.players, this.validStateCheckers,
        this.history);
  }

  /**
   * Gets the tile at the specified coordinate
   *
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
    if (isGameOver()) {
      return Set.of();
    }
    Set<ChessTile> allPieceMovements = piece.getMoves(this);
    validStateCheckers.forEach(v ->
        allPieceMovements.removeIf(entry -> {
          try {
            LOG.debug(String.format("Valid state checker class: %s", v.getClass()));
            return !v.isValid(this, piece, entry);
          } catch (EngineException e) {return false;}
        }));
    return piece.checkTeam(turnManager.getCurrentPlayer()) ? allPieceMovements : Set.of();
  }

  /**
   * Checks all endConditions and returns true if the game is over
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    return turnManager.isGameOver(this);
  }

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @return scores of all players after game over.
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
   * Finds if a chess tile contains an opposing team to a given team
   *
   * @param team to check for
   * @param tile to check for
   * @return if team opposes piece on tile
   */
  public boolean isOpposing(ChessTile tile, int team) {
    return Arrays.stream(this.getPlayer(team).opponentIDs()).anyMatch(o ->
        tile.getPiece().isPresent() && o == tile.getPiece().get().getTeam());
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
   * If finding the captured piece by looking at the square covered by the current piece one move
   * back (e.g. in en passant, the captured piece is on a different square), this function looks at
   * the piece list for both states to find the captured piece
   *
   * @param team is the team of the player
   * @return piece in pastBoard that's missing from present board that is an opponent of team
   */
  public Piece findTakenPiece(int team)
  /*throws PieceNotFoundException*/ {
    List<Piece> pastPieces = this.getHistory().get(this.getHistory().size() - 1).board()
        .getOpponentPieces(team);
    List<Piece> presentPieces = this.getOpponentPieces(team);

    LOG.debug(String.format("Past pieces: %s", pastPieces));
    LOG.debug(String.format("Present pieces: %s", presentPieces));

    return pastPieces.stream().filter(p -> !presentPieces.contains(p)).findFirst().orElse(null);
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
   * @return list of pieces, with each piece mapped to their team
   */
  public Map<Integer, List<Piece>> getPieceList() {
    return pieceList;
  }

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  public Player[] getPlayers() {
    return players.getPlayersArr();
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
   * Gets list of all opponent pieces for a given board
   *
   * @param team to get opponents for
   * @return list of opponent pieces
   */
  public List<Piece> getOpponentPieces(int team) {
    return board.stream().flatMap(List::stream).toList().stream().map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(p -> Arrays.stream(this.getPlayer(team).opponentIDs()).anyMatch(
            oid -> oid == p.getTeam())).toList();
  }

  /**
   * @return current player
   */
  public int getCurrentPlayer() {
    return turnManager.getCurrentPlayer();
  }

  /**
   * Gets the history of moves made
   *
   * @return history of moves
   */
  public HistoryManager getHistory() {
    return history;
  }

  /**
   * Checks if all the pieces are the same
   *
   * @param o to compare to
   * @return if all the pieces on this and o are the same
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChessBoard otherBoard = (ChessBoard) o;
    return getPieces().equals(otherBoard.getPieces());
  }

  /**
   * @return hash of piece list
   */
  @Override
  public int hashCode() {
    return Objects.hash(pieceList);
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

  public int getThisPlayer() {
    return -1;
  }

  /**
   * Get all of the tiles on the board. Used for ChessBoardData
   *
   * @return list of tiles
   */
  private List<List<ChessTile>> getTiles() {
    return board;
  }

  /**
   * Gets all the ValidStateCheckers on the board. Used for ChessBoardData
   *
   * @return list of valid state checkers
   */
  private List<ValidStateChecker> getValidStateCheckers() {
    return validStateCheckers;
  }

  /**
   * Gets the history manager data for the board. Used for ChessBoardData
   *
   * @return history manager data
   */
  private HistoryManagerData getHistoryManagerData() {
    return history.getHistoryManagerData();
  }

  /**
   * Gets the gameplayer object for the board. Used for ChessBoardData
   *
   * @return gameplayer object
   */
  private GamePlayers getGamePlayers() {
    return players;
  }

  /**
   * Gets the ChessBoardData object for the board for JSON serialization.
   *
   * @return ChessBoardData object
   */
  public ChessBoardData getBoardData() {
    return new ChessBoardData(this);
  }

  /**
   * Sets the callback function used to show an exception as a result of an async task
   *
   * @param showAsyncError the callback function to use
   */
  public void setShowAsyncError(BiConsumer<String, String> showAsyncError) {
    this.showAsyncError = (Throwable e) -> showAsyncError.accept(e.getClass().getSimpleName(),
        e.getMessage());
  }

  /**
   * Sets the callback function used to perform a turn update on the UI asynchronously
   *
   * @param performAsyncTurnUpdate the callback function to use
   */
  public void setPerformAsyncTurnUpdate(
      Consumer<TurnUpdate> performAsyncTurnUpdate) {
    this.performAsyncTurnUpdate = performAsyncTurnUpdate;
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

  /**
   * This class is used to setup the chess board. It is used to create the chess board JSON object.
   *
   * @author Ritvik Janamsetty
   */
  public static final class ChessBoardData {

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final ChessTile[][] board;
    private final TurnManagerData turnManagerData;
    private final GamePlayers players;
    private final ValidStateChecker[] validStateCheckers;
    private final HistoryManagerData history;

    /**
     * @param board              the list of tiles that make up the chess board
     * @param players            the list of players in the game
     * @param validStateCheckers the valid state checkers for the game
     * @param turnManagerData    the turn manager data for the game
     * @param history            the history manager data of the game
     */
    public ChessBoardData(List<List<ChessTile>> board, TurnManagerData turnManagerData,
        GamePlayers players,
        List<ValidStateChecker> validStateCheckers,
        HistoryManagerData history) {
      if (board.size() == 0) {
        this.board = new ChessTile[0][0];
      } else {
        this.board = new ChessTile[board.size()][board.get(0).size()];
      }
      for (int i = 0; i < board.size(); i++) {
        for (int j = 0; j < board.get(i).size(); j++) {
          this.board[i][j] = board.get(i).get(j);
        }
      }
      this.turnManagerData = turnManagerData;
      this.players = players;
      this.validStateCheckers = validStateCheckers.toArray(new ValidStateChecker[0]);
      this.history = history;
    }

    /**
     * This method is used to create the chess board JSON object from a chess board.
     *
     * @param board the chess board
     */
    public ChessBoardData(ChessBoard board) {
      this(board.getTiles(), board.getTurnManagerData(), board.getGamePlayers(),
          board.getValidStateCheckers(), board.getHistoryManagerData());
    }

    /**
     * Creates an empty chess board data object
     */
    public ChessBoardData() {
      this(new ArrayList<>(), new TurnManagerData(), new GamePlayers(), new ArrayList<>(),
          new HistoryManagerData());
    }

    /**
     * Converts the chess board data to a chess board
     *
     * @return the chess board
     */
    public ChessBoard toChessBoard() {
      return new ChessBoard(this);
    }

    public List<List<ChessTile>> board() {
      List<List<ChessTile>> board = new ArrayList<>();
      for (ChessTile[] chessTiles : this.board) {
        board.add(Arrays.asList(chessTiles));
      }
      return board;
    }

    public TurnManagerData turnManagerData() {
      return turnManagerData;
    }

    public GamePlayers players() {
      return players;
    }

    public List<ValidStateChecker> validStateCheckers() {
      return Arrays.asList(validStateCheckers);
    }

    public HistoryManagerData history() {
      return history;
    }


  }
}