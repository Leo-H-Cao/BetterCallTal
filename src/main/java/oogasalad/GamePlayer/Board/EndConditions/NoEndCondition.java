package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import java.util.Optional;
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
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
    return Optional.empty();
  }

  @Override
  public boolean hasGameEnded(ChessBoard board) {
    return false;
  }
}
