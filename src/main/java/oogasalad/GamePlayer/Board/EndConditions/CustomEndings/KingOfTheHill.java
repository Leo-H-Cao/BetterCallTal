package oogasalad.GamePlayer.Board.EndConditions.CustomEndings;

import java.util.Map;
import java.util.Optional;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;

public class KingOfTheHill implements EndCondition {

  @Override
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
    return Optional.empty();
  }

  @Override
  public boolean hasGameEnded(ChessBoard board) {
    return false;
  }
}
