package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;

public class TwoMoves implements EndCondition {

  private static final int MULTIPLIER = 2;

  /***
   * Creates an EndCondition object that always ends the game after three moves
   */
  public TwoMoves() {
  }

  @Override
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
    return board.getHistory().size() == getHistoryLength(board.getPlayers().length) ? Optional.of(
        generatePoints(board.getPlayers())) : Optional.empty();
  }

  /***
   * @param numPlayers in the current game
   * @return length of history needed to match 3 moves for the current player size
   */
  private int getHistoryLength(int numPlayers) {
    return 1 + numPlayers * MULTIPLIER;
  }

  /***
   * @return map where every player gets half a point
   */
  private Map<Integer, Double> generatePoints(Player[] players) {
    return Arrays.stream(players).collect(Collectors.toMap(Player::teamID, (e) -> DRAW));
  }
}
