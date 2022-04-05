package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;

public class AtomicEndCondition implements EndCondition {

  /***
   * Creates an end condition that ends the game if the king is exploded
   */
  public AtomicEndCondition() {
  }

  /***
   * @param board to check
   * @return 0 to teams with missing kings, 1 to its opponents, .5 to others, and
   * nothing if both kings present
   */
  @Override
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
    int[] teams = board.getTeams();
    Map<Integer, Double> scores = new HashMap<>();

    Arrays.stream(teams).filter((t) -> !hasMainPiece(t, board)).forEach((t) ->
        scores.put(t, LOSS));

    if(scores.isEmpty()) return Optional.empty();

    Arrays.stream(teams).filter(scores::containsKey).forEach((t) -> Arrays.stream(
        board.getPlayer(t).opponentIDs()).filter((o) -> !scores.containsKey(o)).forEach((o) ->
        scores.put(o, WIN)));

    Arrays.stream(teams).filter((t) -> !scores.containsKey(t)).forEach((t) ->
        scores.put(t, DRAW));
    
    return Optional.of(scores);
  }

  /***
   * @return if the given team has its main piece on the given board
   */
  private boolean hasMainPiece(int team, ChessBoard board) {
    return board.stream().anyMatch((l) -> l.stream()
        .anyMatch((t) -> t.getPieces().stream().filter((p) -> p.checkTeam(team)).anyMatch(
            Piece::isTargetPiece)));
  }
}
