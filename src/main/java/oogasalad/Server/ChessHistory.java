package oogasalad.Server;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;

/**
 * This class is used to store prior states of the game, allowing for checking complex game
 * interactions and rollback to previous states.
 */
public class ChessHistory {

  private final Deque<ChessBoard> historyDeque;
  private final List<ChessBoard> historyList;
  private int currentIndex;


  /**
   * Creates a new chess history that keeps track of the game states. A linked list is used to store
   * the states as it can be referenced by index and be used a queue.
   */
  public ChessHistory() {
    LinkedList<ChessBoard> history = new LinkedList<>();
    historyDeque = history;
    historyList = history;
    currentIndex = 0;
  }

  /**
   * Adds a new state to the history.
   *
   * @param newState The new state to add.
   */
  public void addToHistory(ChessBoard newState) {
    historyDeque.addLast(newState);
    currentIndex++;
  }

  /**
   * Returns the size of the history.
   *
   * @return the size of the history.
   */
  public int getHistorySize() {
    return historyList.size();
  }

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  public ChessBoard getState(int index) {
    return historyList.get(index);
  }

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  public ChessBoard getFirstState() {
    return historyDeque.getFirst();
  }


  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  public ChessBoard getCurrentState() {
    return historyList.get(currentIndex);
  }

  /**
   * Returns the most recent state.
   *
   * @return the most recent state.
   */
  public ChessBoard getLastState() {
    return historyDeque.getLast();
  }

  /**
   * Returns the index of the most recent state.
   *
   * @return the index of the most recent state.
   */
  public int getCurrentIndex() {
    return currentIndex;
  }

  /**
   * Rewinds the history by amount of states.
   *
   * @param amount The number of states to rewind.
   * @return the state that was rewound to.
   */
  public ChessBoard rewindBackStates(int amount) {
    int difference = currentIndex - amount;
    if (difference < 0) {
      return getFirstState();
    } else {
      for (int i = 0; i < amount; i++) {
        historyDeque.removeLast();
      }
    }
    return historyDeque.getLast();
  }

  /**
   * Advances the history by amount of states.
   *
   * @param amount The number of states to advance.
   * @return the state that was advanced to.
   */
  public ChessBoard advanceForwardStates(int amount) {
    int sum = currentIndex + amount;
    if (sum > historyList.size()) {
      return getLastState();
    } else {
      currentIndex = sum;
      return getCurrentState();
    }
  }

  /**
   * Rewinds the history to a certain index.
   *
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  public ChessBoard goToState(int index) {
    currentIndex = index;
    return getCurrentState();
  }

  /**
   * Clears the history.
   */
  public void clearHistory() {
    historyDeque.clear();
    currentIndex = 0;
  }


}
