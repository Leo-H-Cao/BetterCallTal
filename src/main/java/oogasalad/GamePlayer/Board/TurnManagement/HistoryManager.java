package oogasalad.GamePlayer.Board.TurnManagement;

import oogasalad.GamePlayer.Board.ChessBoard;

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
   *
   * @return the state that was cleared to.
   */
  void clearHistory();

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  boolean isEmpty();

}
