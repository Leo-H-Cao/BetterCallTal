package oogasalad.GamePlayer.Board.EndConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;

/***
 * Creates end condition where losing all pieces is a loss
 *
 * @author Vincent Chen
 */
public class LoseAllPieces implements EndCondition {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public LoseAllPieces(){
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
    Map<Integer, Double> scores = new HashMap<>();
    Set<Integer> representedPlayers = new HashSet<>(
        Arrays.stream(board.getPlayers()).map(Player::teamID).toList());
    board.stream().forEach(l -> l.forEach(t ->
        t.getPieces().forEach(p -> representedPlayers.remove(p.getTeam()))));

    if(representedPlayers.isEmpty()) return scores;

    representedPlayers.forEach(p -> {
      scores.put(p, LOSS);
      Arrays.stream(board.getPlayer(p).opponentIDs()).forEach(o -> scores.put(o, WIN));
    });
    Arrays.stream(board.getPlayers()).filter(p -> !scores.containsKey(p.teamID())).forEach(p -> scores.put(p.teamID(), DRAW));

    return scores;
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
