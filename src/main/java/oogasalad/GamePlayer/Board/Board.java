package oogasalad.GamePlayer.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class Board implements Iterable<ChessTile> {

  private final List<List<ChessTile>> board;
  private final GamePlayers players;

  /**
   * Creates a representation of a chessboard with the length and height of board given
   *
   * @param length  the length of the board
   * @param height  the height of the board
   * @param players the players on the board
   */
  public Board(int length, int height, GamePlayers players) {
    this.players = players;
    board = new ArrayList<>();
    IntStream.range(0, height).forEach(i -> {
      List<ChessTile> list = new ArrayList<>();
      IntStream.range(0, length).forEach(j -> list.add(new ChessTile(new Coordinate(i, j))));
      board.add(list);
    });
  }

  /**
   * Creates a representation of a chessboard if a list of lists of chess tiles is already provided
   *
   * @param board   the array of chess tiles
   * @param players the players on the board
   */
  public Board(List<List<ChessTile>> board, GamePlayers players) {
    this.board = board;
    this.players = players;
  }

  /**
   * Creates a representation of a chessboard if a 2D array of chess tiles is already provided
   *
   * @param board   the array of chess tiles
   * @param players the players on the board
   */
  public Board(ChessTile[][] board, GamePlayers players) {
    this.players = players;
    this.board = new ArrayList<>();
    for (ChessTile[] rowTiles : board) {
      List<ChessTile> rows = new ArrayList<>(Arrays.asList(rowTiles));
      this.board.add(rows);
    }
  }

  /**
   * Creates a representation of a chessboard from a prior board object (used for cloning)
   *
   * @param board   the board to clone
   * @param players the players on the board
   */
  public Board(Board board, GamePlayers players) {
    this.players = players;
    this.board = new ArrayList<>();
    for (List<ChessTile> row : board.getBoard()) {
      List<ChessTile> rows = new ArrayList<>();
      for (ChessTile tile : row) {
        rows.add(tile.clone());
      }
      this.board.add(rows);
    }
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
   * @param piece         to get moves from
   * @param currentPlayer the player whose turn it is
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece, int currentPlayer) {
    return piece.checkTeam(currentPlayer) ? piece.getMoves(this) : Set.of();
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
   * Streams over the board
   *
   * @return stream over the board
   */
  public Stream<List<ChessTile>> stream() {
    return board.stream();
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
   * Get the length of the board
   *
   * @return The length of the board
   */
  public int getBoardLength() {
    return board.get(0).size();
  }

  /**
   * Get the height of the board
   *
   * @return The height of the board
   */
  public int getBoardHeight() {
    return board.size();
  }


  /**
   * Private method to get the raw data structure of the board for use in other methods when
   * processing external board objects within the board class
   *
   * @return the raw data structure of the board
   */
  private List<List<ChessTile>> getBoard() {
    return board;
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<ChessTile> iterator() {
    return new ChessBoardIterator(board);
  }

  /**
   * Performs the given action for each element of the {@code Iterable} until all elements have been
   * processed or the action throws an exception.  Actions are performed in the order of iteration,
   * if that order is specified.  Exceptions thrown by the action are relayed to the caller.
   *
   * @param action The action to be performed for each element
   * @throws NullPointerException if the specified action is null
   */
  @Override
  public void forEach(Consumer<? super ChessTile> action) {
    Iterable.super.forEach(action);
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
   * Iterator class to iterate over the board list
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
     * Returns true if the iteration has more elements. (In other words, returns true if next would
     * return an element rather than throwing an exception.)
     *
     * @return if there's another ChessTile
     */
    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return next ChessTile
     * @throws NoSuchElementException if there exists no next element
     */
    @Override
    public ChessTile next() throws NoSuchElementException {
      return queue.poll();
    }
  }

}


