package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import oogasalad.GamePlayer.Board.ChessBoard;

/**
 * This class is used to store prior states of the game, allowing for checking complex game
 * interactions and rollback to previous states.
 */
public class ChessHistory implements HistoryManager {

  private final List<History> history;
  private int currentIndex;


  /**
   * Creates a chess history object, which can be used to store the past game states. Overloaded
   * constructor used to create an empty history.
   */
  public ChessHistory() {
    this(Collections.emptyList());
  }

  /**
   * Creates a chess history object, which can be used to store the past game states.
   *
   * @param history array of chess boards to store in the history
   */
  public ChessHistory(History[] history) {
    this(List.of(history));
  }

  /**
   * Creates a chess history object, which can be used to store the past game states.
   *
   * @param history the collection of states to store.
   */
  public ChessHistory(Collection<History> history) {
    this.history = new ArrayList<>(history);
    this.currentIndex = history.size() - 1;
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  public History add(History newState) {
    if (currentIndex < size() - 1) {
      history.set(currentIndex + 1, newState);
    } else {
      history.add(newState);
    }
    history.add(newState);
    currentIndex++;
    return newState;
  }

  /**
   * Returns the size of the history.
   * <p>NOTE: Do not simply use the size of the history list to determine the current index of the
   * history. Assuming that an undo operation is performed, the current index will be less than the
   * last index of the history.
   *
   * @return the size of the history.
   */
  public int size() {
    return history.size();
  }

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  public History get(int index) {
    if (index < 0) {
      return getFirst();
    } else if (index > history.size()) {
      return getLast();
    } else {
      return history.get(index);
    }
  }

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  public History getFirst() {
    return history.get(0);
  }


  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  public History getCurrent() {
    return history.get(currentIndex);
  }

  /**
   * Gets the current chess board state of the game.
   *
   * @return the current chess board state of the game.
   */
  public ChessBoard getCurrentBoard() {
    return getCurrent().board();
  }

  /**
   * Returns the most recent state.
   *
   * @return the most recent state.
   */
  public History getLast() {
    return history.get(size() - 1);
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
   * Rewinds the history to a certain index.
   *
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  public History goToState(int index) {
    currentIndex = index;
    return getCurrent();
  }

  /**
   * Clears the history.
   *
   * @return the state that was cleared to.
   */
  public ChessHistory clearHistory() {
    history.clear();
    currentIndex = 0;
    return this;
  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  public boolean isEmpty() {
    return history.isEmpty();
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
    return currentIndex == that.currentIndex && history.equals(that.history);
  }

  /**
   * Hashes the history.
   *
   * @return the hashcode of the history.
   */
  @Override
  public int hashCode() {
    return Objects.hash(history, currentIndex);
  }
}
