package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;

public class Conquer implements EndCondition {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public Conquer() {
    super();
  }

  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return null;
  }

  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
