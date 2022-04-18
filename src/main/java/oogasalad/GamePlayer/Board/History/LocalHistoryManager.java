package oogasalad.GamePlayer.Board.History;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;

/**
 * This class is used to store prior states of the game, allowing for checking complex game
 * interactions and rollback to previous states.
 */
public class LocalHistoryManager implements HistoryManager {

  private List<History> history;
  private int currentIndex;


  /**
   * Creates a chess history object, which can be used to store the past game states. Overloaded
   * constructor used to create an empty history.
   */
  public LocalHistoryManager() {
    this(Collections.emptyList());
  }

  /**
   * Creates a chess history object, which can be used to store the past game states.
   *
   * @param history the collection of states to store.
   */
  public LocalHistoryManager(Collection<History> history) {
    this.history = new ArrayList<>(history);
    this.currentIndex = history.size() - 1;
  }

  /**
   * Creates a chess history object from a past history object, which can be used to store the past
   * game states.
   *
   * @param history the past history object to create a new history object from.
   */
  public LocalHistoryManager(HistoryManager history) {
    this(history.stream().toList());
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  public History add(History newState) {
    if (currentIndex < size() - 1) {
      history.set(currentIndex + 1, newState);
      history = history.subList(0, currentIndex + 2);
    } else {
      history.add(newState);
    }
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
  public void clearHistory() {
    history.clear();
    currentIndex = 0;
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
   * @return stream over history list
   */
  @Override
  public Stream<History> stream() {
    return history.stream();
  }
}
