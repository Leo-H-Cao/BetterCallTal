package oogasalad.GamePlayer.Server;

import oogasalad.GamePlayer.Board.ChessBoard;

/**
 * Interface for managing all turn interactions. it has two implementations: a local and a server
 * implementation. The local implementation is used for local games and the server implementation is
 * used for networked games.
 */
public interface TurnManager {

  /**
   * Updates the turn manager with the current board.
   *
   * @return the incre
   */
  int incrementTurn();

  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  int getCurrentPlayer();

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  ChessBoard getCurrentBoard();

  /**
   * Adds a new state to the history.
   *
   * @param board The new state to add.
   */
  ChessBoard addToHistory(ChessBoard board);

  /**
   * Returns the size of the history.
   *
   * @return the size of the history.
   */
  int getHistorySize();

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  boolean isHistoryEmpty();

  /**
   * Checks all endConditions and
   *
   * @return if the game is over
   */
  boolean isGameOver();

}
