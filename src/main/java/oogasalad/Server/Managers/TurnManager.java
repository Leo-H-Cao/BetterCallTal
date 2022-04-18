package oogasalad.Server.Managers;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;

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

}
