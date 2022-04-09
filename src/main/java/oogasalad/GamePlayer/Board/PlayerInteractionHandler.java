package oogasalad.GamePlayer.Board;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

/**
 * This class is responsible for checking if the game has ended and managing the scores of the
 * players.
 *
 * @author Jed Yang
 * @author Vincent Chen
 * @author Ritvik Janamsetty
 */
public class PlayerInteractionHandler {

  private final PriorityQueue<EndCondition> endConditions;
  private final TurnCriteria turn;
  private final Player[] players;
  private final int[] teamNums;
  private Map<Integer, Double> endResult;
  private int currentPlayer;

  /**
   * Creates a PlayerInteractionHandler with the given Players, TurnCriteria, and EndConditions to
   * check. End conditions can either be an array or a long list of params.
   *
   * @param players    the players of the game
   * @param turn       the TurnCriteria to check
   * @param conditions the EndConditions to check
   */
  public PlayerInteractionHandler(Player[] players, TurnCriteria turn, EndCondition... conditions) {
    this(players, turn, Arrays.asList(conditions));
  }

  /**
   * Creates a PlayerInteractionHandler with the given Players, TurnCriteria, and EndConditions to
   * check. End conditions need to be an implementation of Iterable.
   *
   * @param players    the players of the game
   * @param turn       the TurnCriteria to check
   * @param conditions the EndConditions to check
   */
  public PlayerInteractionHandler(Player[] players, TurnCriteria turn,
      Iterable<EndCondition> conditions) {
    this.players = players;
    this.turn = turn;
    teamNums = getTeamNums(players);
    endConditions = new PriorityQueue<>();
    for (EndCondition condition : conditions) {
      endConditions.add(condition);
    }
    endResult = new HashMap<>();
    currentPlayer = 0;
  }

  /**
   * Adds a new EndCondition to the end of the priority of EndConditions to check.
   *
   * @param condition the EndCondition to add
   */
  public void addEndCondition(EndCondition condition) {
    endConditions.add(condition);
  }

  /**
   * Checks if the game is over. If it is, the scores are calculated and the
   */
  public boolean isGameOver(ChessBoard board) {
    for (EndCondition ec : endConditions) {
      Map<Integer, Double> result = ec.getScores(board);
      if (!result.isEmpty()) {
        endResult = result;
        return true;
      }
    }
    return false;
  }

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @return scores of all players after game over.
   */
  public Map<Integer, Double> getScores() {
    return Collections.unmodifiableMap(endResult);
  }

  /**
   * Gets the player object with the associated ID
   *
   * @param id of player
   * @return player with given id
   */
  public Player getPlayer(int id) {
    return players[Math.min(id, players.length - 1)];
  }

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  public Player[] getPlayers() {
    return players;
  }

  /**
   * Gets all team numbers
   *
   * @return team numbers for all players
   */
  public int[] getTeams() {
    return teamNums;
  }

  /**
   * Convert players to team numbers
   *
   * @return team nums associated with each player
   */
  private int[] getTeamNums(Player[] players) {
    return Arrays.stream(players).mapToInt(Player::teamID).toArray();
  }

  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  public final int getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Increments turn
   *
   * @return int player id after turn is made
   */
  public int incrementTurn() {
    currentPlayer = turn.incrementTurn();
    return currentPlayer;
  }

}
