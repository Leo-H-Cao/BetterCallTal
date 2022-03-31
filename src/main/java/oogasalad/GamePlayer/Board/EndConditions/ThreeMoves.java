package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import java.util.Optional;
import oogasalad.GamePlayer.Board.ChessBoard;

public class ThreeMoves implements EndCondition {

  @Override
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
  }
}
