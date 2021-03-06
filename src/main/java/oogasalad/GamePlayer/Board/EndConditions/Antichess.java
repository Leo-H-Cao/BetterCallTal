package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;

/***
 * Creates a game mode where the first to lose all pieces wins
 *
 * @author Vincent Chen
 */
public class Antichess extends LoseAllPieces {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public Antichess() {
    super();
  }

  /***
   * Losers are the ones who lose all pieces, winners are their opponents, draw for everyone else
   *
   * @param board to get scores from
   * @return read above
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    Map<Integer, Double> invertedMap = super.getScores(board);
    return invertedMap.keySet().stream().collect(Collectors.toMap(k -> k,
        k -> invertScore(invertedMap.get(k))));
  }

  /***
   * Inverts the given score with the equation y = 1-x
   * LOSS (0) -> WIN (1)
   * DRAW (0.5) -> DRAW (0.5)
   * WIN (1) -> LOSS (0)
   *
   * @param val to invert
   * @return 1-val
   */
  private double invertScore(double val) {
    return WIN - val;
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
