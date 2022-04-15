package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;

public class Conquer implements EndCondition {

  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return null;
  }

  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
