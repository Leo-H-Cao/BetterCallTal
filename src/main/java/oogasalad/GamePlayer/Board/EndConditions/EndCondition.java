package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import java.util.Optional;
import oogasalad.GamePlayer.Board.ChessBoard;

public interface EndCondition extends Comparable<EndCondition> {

  double WIN = 1.0;
  double DRAW = 0.5;
  double LOSS = 0.0;

  /**
   * Determines if the game is over, and if so, returns the points awarded to each team
   *
   * @return the point values awarded to every team if a game is over, else empty
   */
  Map<Integer, Double> getScores(ChessBoard board);

}
