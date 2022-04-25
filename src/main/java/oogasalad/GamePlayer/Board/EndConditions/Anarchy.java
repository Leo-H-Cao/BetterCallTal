package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;

public class Anarchy implements EndCondition {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public Anarchy() {
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
