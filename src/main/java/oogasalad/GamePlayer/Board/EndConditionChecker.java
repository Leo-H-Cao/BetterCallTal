package oogasalad.GamePlayer.Board;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;

/**
 * This class is responsible for checking if the game has ended and managing the scores of the
 * players.
 *
 * @author Jed Yang
 * @author Vincent Chen
 * @author Ritvik Janamsetty
 */
public class EndConditionChecker {

  private final PriorityQueue<EndCondition> endConditions;
  private Map<Integer, Double> endResult;

  /**
   * Creates a EndConditionChecker with the given EndConditions to check and keep track of points
   * for. The EndConditions are checked in the order they are given.
   *
   * @param conditions the EndConditions to check
   */
  public EndConditionChecker(EndCondition... conditions) {
    endConditions = new PriorityQueue<>(Arrays.asList(conditions));
    endResult = new HashMap<>();
  }

  /**
   * Creates a EndConditionChecker with the given EndConditions to check and keep track of points
   * for. The EndConditions are checked in the order the iterable returns them.
   *
   * @param conditions the EndConditions to check
   */
  public EndConditionChecker(Iterable<EndCondition> conditions) {
    endConditions = new PriorityQueue<>();
    for (EndCondition condition : conditions) {
      endConditions.add(condition);
    }
    endResult = new HashMap<>();
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
}
