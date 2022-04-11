package oogasalad.GamePlayer.Server;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import oogasalad.GamePlayer.Board.History;

/**
 * This class is used to store prior states of the game, allowing for checking complex game
 * interactions and rollback to previous states.
 */
public class ChessHistory {

  private Deque<History> historyDeque;
  private List<History> historyList;
  private int currentIndex;


  /**
   * Creates a chess history object, which can be used to store the past game states. Overloaded
   * constructor used to create an empty history.
   */
  public ChessHistory() {
    setUpHistory(Collections.emptyList());
  }

  /**
   * Creates a chess history object, which can be used to store the past game states.
   *
   * @param history array of chess boards to store in the history
   */
  public ChessHistory(History[] history) {
    setUpHistory(List.of(history));
  }

  /**
   * Creates a chess history object, which can be used to store the past game states.
   *
   * @param history the collection of states to store.
   */
  public ChessHistory(Collection<History> history) {
    setUpHistory(history);
  }

  /**
   * Private set the datastructures used to store the history.
   *
   * @param history collection of states to store.
   */
  private void setUpHistory(Collection<History> history) {
    this.historyDeque = new LinkedList<>(history);
    this.historyList = new LinkedList<>(history);
    this.currentIndex = history.size() - 1;
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  public History add(History newState) {
    historyDeque.addLast(newState);
    currentIndex++;
    return newState;
  }

  /**
   * Returns the size of the history.
   *
   * @return the size of the history.
   */
  public int size() {
    return historyList.size();
  }

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  public History getState(int index) {
    if (index < 0) {
      return getFirstState();
    } else if (index > historyList.size()) {
      return getLastState();
    } else {
      return historyList.get(index);
    }
  }

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  public History getFirstState() {
    return historyDeque.getFirst();
  }


  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  public History getCurrentState() {
    return historyList.get(currentIndex);
  }

  /**
   * Returns the most recent state.
   *
   * @return the most recent state.
   */
  public History getLastState() {
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
  public History rewindBackStates(int amount) {
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
  public History advanceForwardStates(int amount) {
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
  public History goToState(int index) {
    currentIndex = index;
    return getCurrentState();
  }

  /**
   * Clears the history.
   *
   * @return the state that was cleared to.
   */
  public ChessHistory clearHistory() {
    historyDeque.clear();
    currentIndex = 0;
    return this;
  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  public boolean isEmpty() {
    return historyDeque.isEmpty();
  }

  /**
   * Checks if two histories are equal.
   *
   * @param o the object to compare to.
   * @return true if the two histories are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ChessHistory that)) {
      return false;
    }
    return currentIndex == that.currentIndex && historyList.equals(that.historyList);
  }

  /**
   * Hashes the history.
   *
   * @return the hashcode of the history.
   */
  @Override
  public int hashCode() {
    return Objects.hash(historyList, currentIndex);
  }
}
