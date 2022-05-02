package oogasalad.GamePlayer.Board.History;

import java.util.function.Consumer;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;

/**
 * This class is used to store prior states of the game. I chose this class as an example of a
 * Masterpiece code since it showcase my abstraction, encapsulations, and skillful use of robust yet
 * simple, stable APIs.
 * <p>
 * 1. https://coursework.cs.duke.edu/compsci308_2022spring/oogasalad_BetterCallTal/-/commit/bfd2e7c3fbe8ef3a1acb2ce667372df467be94b2
 * 2. https://coursework.cs.duke.edu/compsci308_2022spring/oogasalad_BetterCallTal/-/commit/0ec8151813409679bba0d19967ea555d949ce87f
 * <p>
 * Changes for Masterpiece: Auto Formatting
 */
public interface HistoryManager {

  /**
   * Gets the current chess board state of the game.
   *
   * @return the current chess board state of the game.
   */
  ChessBoard getCurrentBoard();

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  History add(History newState);

  /**
   * Returns the most recent state.
   *
   * @return the most recent state.
   */
  History getLast();

  /**
   * Returns the size of the history.
   * <p>NOTE: Do not simply use the size of the history list to determine the current index of the
   * history. Assuming that an undo operation is performed, the current index will be less than the
   * last index of the history.
   *
   * @return the size of the history.
   */
  int size();

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  History get(int index);

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  History getFirst();

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  History getCurrent();

  /**
   * Returns the index of the most recent state.
   *
   * @return the index of the most recent state.
   */
  int getCurrentIndex();

  /**
   * Rewinds the history to a certain index.
   *
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  History goToState(int index);

  /**
   * Clears the history.
   */
  void clearHistory();

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  boolean isEmpty();

  /**
   * Gets a stream of the history.
   *
   * @return way to stream over the history
   */
  Stream<History> stream();

  /**
   * Gets the history API link for the current history manager data. Returns an empty string if the
   * history manager is a local history manager.
   *
   * @return the history API link for the current history manager data.
   */
  String getLink();

  /**
   * Adds a link to the history manager
   *
   * @param link the link to add
   */
  void setLink(String link);

  /**
   * Gets the history manager data.
   *
   * @return the history manager data.
   */
  HistoryManagerData getHistoryManagerData();

  /**
   * Sets a callback to handle any sort of error that occurs during the game.
   *
   * @param errorHandler the error handler to set
   */
  void setErrorHandler(Consumer<Throwable> errorHandler);

}
