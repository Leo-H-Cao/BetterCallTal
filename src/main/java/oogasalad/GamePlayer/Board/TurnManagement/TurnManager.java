package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;

/**
 * Interface for managing all turn interactions. it has two implementations: a local and a server
 * implementation. The local implementation is used for local games and the server implementation is
 * used for networked games.
 */
public interface TurnManager {

  /**
   * Adds a new EndCondition to the end of the priority of EndConditions to check.
   *
   * @param condition the EndCondition to add
   */
  void addEndCondition(EndCondition condition);

  /**
   * Updates the turn manager with the current board.
   *
   * @return the current player
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
  void addToHistory(History board);

  /**
   * Returns the first state of the game.
   *
   * @return the first state of the game.
   */
  ChessBoard getFirstBoard();

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
   * Checks all endConditions and returns true if the game is over.
   *
   * @param board the board to check
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver(ChessBoard board);

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @return scores of all players after game over.
   */
  Map<Integer, Double> getScores();

  /**
   * Gets all the players playing in the game
   *
   * @return all players playing in the game
   */
  GamePlayers getGamePlayers();

}
