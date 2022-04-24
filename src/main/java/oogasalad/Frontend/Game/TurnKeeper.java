package oogasalad.Frontend.Game;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
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

  /**
   * Gets the turn criteria for the game
   *
   * @return the turn criteria for the game
   */
  @Override
  public TurnCriteria getTurnCriteria() {
    return null;
  }

  /**
   * Gets the end conditions for the game
   *
   * @return the end conditions for the game
   */
  @Override
  public Collection<EndCondition> getEndConditions() {
    return null;
  }

  /**
   * Gets the turn manager API link for the current history manager data. Returns an empty string if
   * the history manager is a turn manager.
   *
   * @return the turn manager API link for the current turn manager data.
   */
  @Override
  public String getLink() {
    return null;
  }
}
