package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Like stalemate but the person who stalemates the other wins instead of being a draw
 */
public class StalemateWin extends Stalemate {

  private static final Logger LOG = LogManager.getLogger(StalemateWin.class);

  /**
   * Puts loss for stalemated team, win for their enemies, and draw for everyone else
   *
   * @param board to get scores from
   * @return map of scores as listed above
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    try {
      if(isStalemate(board)) {
        Map<Integer, Double> scores = new HashMap<>();
        scores.put(board.getCurrentPlayer(), LOSS);
        Arrays.stream(board.getPlayer(board.getCurrentPlayer()).opponentIDs()).forEach(p ->
          scores.put(p, WIN));
        Arrays.stream(board.getPlayers()).filter(p -> !scores.containsKey(p.teamID())).forEach(p ->
          scores.put(p.teamID(), DRAW));
        return scores;
      }
      return Collections.emptyMap();
    } catch (EngineException e) {
      LOG.warn("Stalemate exception");
      return Collections.emptyMap();
    }
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
