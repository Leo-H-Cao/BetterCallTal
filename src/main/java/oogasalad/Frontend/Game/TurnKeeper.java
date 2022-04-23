package oogasalad.Frontend.Game;

import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;

public class TurnKeeper implements TurnManager {

  public static final String AI = "ai";
  public static final String SERVER = "server";


  private List<String> turn;
  private int counter;

  public TurnKeeper(String[] players) {
    turn = List.of(players);
    counter = 0;
  }

  public boolean isAITurn() {
    String ret = turn.get(counter % turn.size());
    counter += 1;
    return ret.equals(AI);
  }

  public boolean hasAI() {
    return turn.stream().anyMatch(e -> e.equals(AI));
  }

  @Override
  public int incrementTurn() {
    counter++;
    return counter;
  }

  @Override
  public int getCurrentPlayer() {
    if (turn.get(counter % turn.size()).equals(AI)) {
      return 1;
    }
    return 0;
  }

  @Override
  public boolean isGameOver(ChessBoard board) {
    return false;
  }

  @Override
  public Map<Integer, Double> getScores() {
    return null;
  }

  @Override
  public GamePlayers getGamePlayers() {
    return null;
  }

  public boolean hasRemote() {
    return turn.stream().anyMatch(e -> e.equals(AI) || e.equals(SERVER));
  }
}
