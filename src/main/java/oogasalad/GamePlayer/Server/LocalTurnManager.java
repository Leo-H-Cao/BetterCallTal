package oogasalad.GamePlayer.Server;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditionChecker;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

/**
 * Manages all turn interactions in a local game. To implement the same interact regardless whether
 * the game is played locally or remotely, this class has rather simple methods with each method
 * simply calling the corresponding method in the appropriate object.
 */
public class LocalTurnManager implements TurnManager {

  private final ChessHistory history;
  private final TurnCriteria turn;
  private final EndConditionChecker endChecker;

  //TO-DO: implement a builder pattern for this class once class finalization is complete
  public LocalTurnManager(ChessHistory history, TurnCriteria turn, EndConditionChecker endChecker) {
    this.history = history;
    this.turn = turn;
    this.endChecker = endChecker;
  }

  /**
   * Updates the turn manager with the current board.
   *
   * @return the next player's turn.
   */
  @Override
  public int incrementTurn() {
    return turn.incrementTurn();
  }

  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  @Override
  public int getCurrentPlayer() {
    return turn.getCurrentPlayer();
  }

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  @Override
  public ChessBoard getCurrentBoard() {
    return history.getCurrentState();
  }

  /**
   * Adds a new state to the history.
   *
   * @param board The new state to add.
   */
  @Override
  public ChessBoard addToHistory(ChessBoard board) {
    return history.add(board);
  }

  /**
   * Returns the size of the history.
   *
   * @return the size of the history.
   */
  @Override
  public int getHistorySize() {
    return history.size();
  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  @Override
  public boolean isHistoryEmpty() {
    return history.isEmpty();
  }

  /**
   * Checks all endConditions and
   *
   * @return if the game is over
   */
  @Override
  public boolean isGameOver(ChessBoard board) {
    return endChecker.isGameOver(board);
  }
}
