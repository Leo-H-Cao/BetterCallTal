package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;

public class Antichess implements EndCondition {

  /***
   * Losers are the ones who lose all pieces, winners are their opponents, draw for everyone else
   *
   * @param board to get scores from
   * @return read above
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return null;
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
