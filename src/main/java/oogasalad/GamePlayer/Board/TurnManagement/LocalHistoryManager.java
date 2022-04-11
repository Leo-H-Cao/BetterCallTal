package oogasalad.GamePlayer.Board.TurnManagement;

import oogasalad.GamePlayer.Board.ChessBoard;

public class LocalHistoryManager implements HistoryManager {

  private final ChessHistory history;

  public LocalHistoryManager() {
    history = new ChessHistory();
  }

  /**
   * Gets the current chess board state of the game.
   *
   * @return the current chess board state of the game.
   */
  @Override
  public ChessBoard getCurrentBoard() {
    return history.getCurrentBoard();
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  @Override
  public History add(History newState) {
    return history.add(newState);
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
    return history.size();
  }

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  @Override
  public History get(int index) {
    return history.get(index);
  }

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  @Override
  public History getFirst() {
    return history.getFirst();
  }

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  @Override
  public History getCurrent() {
    return history.getCurrent();
  }

  /**
   * Returns the index of the most recent state.
   *
   * @return the index of the most recent state.
   */
  @Override
  public int getCurrentIndex() {
    return history.getCurrentIndex();
  }

  /**
   * Rewinds the history to a certain index.
   *
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  @Override
  public History goToState(int index) {
    return history.goToState(index);
  }

  /**
   * Clears the history.
   *
   * @return the state that was cleared to.
   */
  @Override
  public ChessHistory clearHistory() {
    return history.clearHistory();
  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  @Override
  public boolean isEmpty() {
    return history.isEmpty();
  }
}