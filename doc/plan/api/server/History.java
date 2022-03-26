package oogasalad.server;

/**
 * This class is used to store prior states of the game, allowing for checking complex game
 * interactions and rollbacking to previous states.
 */
public interface History {

  /**
   * Adds a new state to the history.
   * @param newState The new state to add.
   */
  public void addToHistory(State newState);

  /**
   * Returns the size of the history.
   * @return the size of the history.
   */
  public int getHistorySize();

  /**
   * Returns the state at a given index.
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  public State getState(int index);

  /**
   * Returns the most recent state.
   * @return the most recent state.
   */
  public State getCurrentState();

  /**
   * Returns the index of the most recent state.
   * @return the index of the most recent state.
   */
  public State getCurrentIndex();

  /**
   * Rewinds the history by amount of states.
   * @param amount The number of states to rewind.
   * @return the state that was rewound to.
   */
  public State rewindBackStates(int amount);

  /**
   * Advances the history by amount of states.
   * @param amount The number of states to advance.
   * @return the state that was advanced to.
   */
  public State advanceForwardStates(int amount);

  /**
   * Rewinds the history to a certain index.
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  public State goToState(int index);

  /**
   * Clears the history.
   */
  public void clearHistory();

}