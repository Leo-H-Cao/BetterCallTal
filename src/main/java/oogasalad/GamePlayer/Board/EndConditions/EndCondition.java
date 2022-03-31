package oogasalad.GamePlayer.Board.EndConditions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import oogasalad.GamePlayer.Board.ChessBoard;

public interface EndCondition {

  /***
   * Determines if the game is over, and if so, returns the points awarded to each team
   *
   * @return the point values awarded to every team if a game is over, else empty
   */
  Optional<Map<Integer, Double>> getScores(ChessBoard board);
}
