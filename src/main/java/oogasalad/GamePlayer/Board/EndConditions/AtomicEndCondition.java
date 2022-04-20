package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;

/**
 * Creates an end condition that ends the game if the king is exploded
 */
public class AtomicEndCondition implements EndCondition {

  /**
   * @param board to check
   * @return 0 to teams with missing kings, 1 to its opponents, .5 to others, and nothing if both
   * kings present
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    int[] teams = board.getTeams();
    Map<Integer, Double> scores = new HashMap<>();

    Arrays.stream(teams).filter(t -> !hasMainPiece(t, board)).forEach(t ->
        scores.put(t, LOSS));

    if (scores.isEmpty()) {
      return scores;
    }

    Arrays.stream(teams).filter(scores::containsKey).forEach(t -> Arrays.stream(
        board.getPlayer(t).opponentIDs()).filter(o -> !scores.containsKey(o)).forEach(o ->
        scores.put(o, WIN)));

    Arrays.stream(teams).filter((t) -> !scores.containsKey(t)).forEach(t ->
        scores.put(t, DRAW));

    return scores;
  }

  /***
  /**
   * @return if the given team has its main piece on the given board
   */
  private boolean hasMainPiece(int team, ChessBoard board) {
    return board.stream().anyMatch(l -> l.stream()
        .anyMatch(t -> t.getPieces().stream().filter(p -> p.checkTeam(team)).anyMatch(
            Piece::isTargetPiece)));
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
