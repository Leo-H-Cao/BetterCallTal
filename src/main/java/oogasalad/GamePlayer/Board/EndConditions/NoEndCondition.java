package oogasalad.GamePlayer.Board.EndConditions;

import java.util.HashMap;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;

public class NoEndCondition implements EndCondition {

  /***
   * Creates an EndCondition object where the game never ends
   */
  public NoEndCondition() {}

  /***
   * @return nothing, as the game never ends
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return new HashMap<>();
  }

}
