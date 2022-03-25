package oogasalad.GamePlayer.Board;

import java.util.List;
import java.util.Map;

public interface EndCondition {

  /***
   * Determines if the game is over, and if so, returns the points awarded to each team
   *
   * @return the point values awarded to every team if a game is over, else an empty map
   */
  Map<Integer, Double> getScores(ChessBoard board);
}
