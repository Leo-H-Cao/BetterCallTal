package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.Collection;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

/**
 * Interface for managing all turn interactions. it has two implementations: a local and a server
 * implementation. The local implementation is used for local games and the server implementation is
 * used for networked games.
 */
public interface TurnManager {

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

  /**
   * Gets the turn criteria for the game
   *
   * @return the turn criteria for the game
   */
  TurnCriteria getTurnCriteria();

  /**
   * Gets the end conditions for the game
   *
   * @return the end conditions for the game
   */
  Collection<EndCondition> getEndConditions();

  /**
   * Gets the turn manager API link for the current history manager data. Returns an empty string if
   * the history manager is a turn manager.
   *
   * @return the turn manager API link for the current turn manager data.
   */
  String getLink();
}
