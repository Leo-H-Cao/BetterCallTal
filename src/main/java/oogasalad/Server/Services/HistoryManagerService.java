package oogasalad.Server.Services;

import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.History.History;

public interface HistoryManagerService {

  /**
   * Gets the current chess board state of the game.
   *
   * @param id The id of the game.
   * @return the current chess board state of the game.
   */
  ChessBoard getCurrentBoard(String id);

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param id       The id of the game.
   * @param newState The new state to add.
   */
  History add(String id, History newState);

  /**
   * Returns the most recent state.
   *
   * @param id The id of the game.
   * @return the most recent state.
   */
  History getLast(String id);

  /**
   * Returns the size of the history.
   * <p>NOTE: Do not simply use the size of the history list to determine the current index of the
   * history. Assuming that an undo operation is performed, the current index will be less than the
   * last index of the history.
   *
   * @param id The id of the game.
   * @return the size of the history.
   */
  int size(String id);

  /**
   * Returns the state at a given index.
   *
   * @param id    The id of the game.
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  History get(String id, int index);

  /**
   * Returns the starting board configuration.
   *
   * @param id The id of the game.
   * @return the starting board configuration.
   */
  History getFirst(String id);

  /**
   * Returns the current state of the game.
   *
   * @param id The id of the game.
   * @return the current state of the game.
   */
  History getCurrent(String id);

  /**
   * Returns the index of the most recent state.
   *
   * @param id The id of the game.
   * @return the index of the most recent state.
   */
  int getCurrentIndex(String id);

  /**
   * Rewinds the history to a certain index.
   *
   * @param id    The id of the game.
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  History goToState(String id, int index);

  /**
   * Clears the history.
   *
   * @param id The id of the game.
   * @return the state that was cleared to.
   */
  void clearHistory(String id);

  /**
   * Returns whether the history is empty.
   *
   * @param id The id of the game.
   * @return true if the history is empty, false otherwise.
   */
  boolean isEmpty(String id);

  /**
   * @param id The id of the game.
   * @return way to stream over the history
   */
  Stream<History> stream(String id);
}
