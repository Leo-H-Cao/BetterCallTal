package oogasalad.GamePlayer.Server;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;

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
   * @param board the board to check
   * @return if the game is over
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
   * Gets the player object with the associated ID
   *
   * @param id of player
   * @return player with given id
   */
  Player getPlayer(int id);

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  Player[] getPlayers();

  /**
   * Gets all team numbers
   *
   * @return team numbers for all players
   */
  int[] getTeams();

}
