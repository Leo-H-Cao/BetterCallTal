package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;

/***
 * Creates an EndCondition object that always ends the game after three moves
 *
 * @author Vincent Chen
 */
public class TwoMoves implements EndCondition {

  private static final int MULTIPLIER = 2;

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public TwoMoves() {
    super();
  }

  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return board.getHistory().size() == getHistoryLength(board.getPlayers().length) ?
        generatePoints(board.getPlayers()) : new HashMap<>();
  }

  /***
   /**
   * @param numPlayers in the current game
   * @return length of history needed to match 3 moves for the current player size
   */
  private int getHistoryLength(int numPlayers) {
    return 1 + numPlayers * MULTIPLIER;
  }

  /**
   * @return map where every player gets half a point
   */
  private Map<Integer, Double> generatePoints(Player[] players) {
    return Arrays.stream(players).collect(Collectors.toMap(Player::teamID, e -> DRAW));
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
