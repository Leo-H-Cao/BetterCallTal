package oogasalad.GamePlayer.Board.History;

import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;

public record RemoteHistoryManager(String id) implements HistoryManager {

  private static final String BASE_URL = "http://localhost:8080/turns";
  private static final String CURRENT_BOARD = BASE_URL + "/current-board/%s";
  private static final String ADD = BASE_URL + "/add/%s";
  private static final String LAST = BASE_URL + "/last/%s";
  private static final String SIZE = BASE_URL + "/size/%s";
  private static final String GET = BASE_URL + "/index/%s/%d";
  private static final String GET_FIRST = BASE_URL + "/first/%s";
  private static final String GET_LAST = BASE_URL + "/last/%s";
  private static final String GET_CURRENT = BASE_URL + "/current/%s";
  private static final String GET_CURRENT_INDEX = BASE_URL + "/current-index/%s";
  private static final String REWIND = BASE_URL + "/rewind/%s/%d";
  private static final String CLEAR = BASE_URL + "/clear/%s";
  private static final String IS_EMPTY = BASE_URL + "/empty/%s";
  private static final String IS_FULL = BASE_URL + "/stream/%s";

  /**
   * Gets the current chess board state of the game.
   *
   * @return the current chess board state of the game.
   */
  @Override
  public ChessBoard getCurrentBoard() {
    String url = String.format(CURRENT_BOARD, id);
    return null;
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  @Override
  public History add(History newState) {
    String url = String.format(ADD, id);
    return null;
  }

  /**
   * Returns the most recent state.
   *
   * @return the most recent state.
   */
  @Override
  public History getLast() {

    return null;
  }

  /**
   * Returns the size of the history.
   * <p>NOTE: Do not simply use the size of the history list to determine the current index of the
   * history. Assuming that an undo operation is performed, the current index will be less than the
   * last index of the history.
   *
   * @return the size of the history.
   */
  @Override
  public int size() {
    return 0;
  }

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  @Override
  public History get(int index) {
    return null;
  }

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  @Override
  public History getFirst() {
    return null;
  }

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  @Override
  public History getCurrent() {
    return null;
  }

  /**
   * Returns the index of the most recent state.
   *
   * @return the index of the most recent state.
   */
  @Override
  public int getCurrentIndex() {
    return 0;
  }

  /**
   * Rewinds the history to a certain index.
   *
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  @Override
  public History goToState(int index) {
    return null;
  }

  /**
   * Clears the history.
   *
   * @return the state that was cleared to.
   */
  @Override
  public void clearHistory() {

  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Gets a stream of the history.
   *
   * @return way to stream over the history
   */
  @Override
  public Stream<History> stream() {
    return null;
  }

  /**
   * Gets the history API link for the current history manager data. Returns an empty string if the
   * history manager is a local history manager.
   *
   * @return the history API link for the current history manager data.
   */
  @Override
  public String getLink() {
    return id;
  }

  /**
   * Gets the history manager data.
   *
   * @return the history manager data.
   */
  @Override
  public HistoryManagerData getHistoryManagerData() {
    return null;
  }
}
