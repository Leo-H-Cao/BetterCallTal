package oogasalad.GamePlayer.Board.EndConditions.CustomEndings;

import java.util.Map;
import java.util.Optional;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;

public class Anarchy implements EndCondition {

  @Override
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
    return Optional.empty();
  }
}
