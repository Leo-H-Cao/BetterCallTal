package oogasalad.Server.Services;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;

/**
 * Interface for managing all turn interactions. it has two implementations: a local and a server
 * implementation. The local implementation is used for local games and the server implementation is
 * used for networked games.
 */
public interface TurnManagerService {

  /**
   * Updates the turn manager with the current board.
   *
   * @param id the id of the game
   * @return the current player
   */
  int incrementTurn(String id);

  /**
   * Determines which player is currently playing
   *
   * @param id the id of the game
   * @return int player id
   */
  int getCurrentPlayer(String id);

  /**
   * Checks all endConditions and returns true if the game is over.
   *
   * @param id    the id of the game
   * @param board the board to check
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver(String id, ChessBoard board);

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @param id the id of the game
   * @return scores of all players after game over.
   */
  Map<Integer, Double> getScores(String id);

  /**
   * Gets all the players playing in the game
   *
   * @param id the id of the game
   * @return all players playing in the game
   */
  GamePlayers getGamePlayers(String id);

}
