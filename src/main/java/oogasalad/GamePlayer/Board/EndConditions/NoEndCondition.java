package oogasalad.GamePlayer.Board.EndConditions;

import java.util.HashMap;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;

/***
 * Creates an end condition where the game never ends
 *
 * @author Vincent Chen
 */
public class NoEndCondition implements EndCondition {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public NoEndCondition() {
    super();
  }

  /***
   * @return nothing, as the game never ends
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return new HashMap<>();
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
