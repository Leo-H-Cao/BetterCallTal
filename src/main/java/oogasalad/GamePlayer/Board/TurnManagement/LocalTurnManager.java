package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Consumer;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

/**
 * Manages all turn interactions in a local game. To implement the same interact regardless whether
 * the game is played locally or remotely, this class has rather simple methods with each method
 * simply calling the corresponding method in the appropriate object. Thus, this class is
 * intentionally designed not be active and serve as nothing more than a wrapper for the
 * PlayerInteractionHandler to allow a same interface for both local and remote games.
 */
public class LocalTurnManager implements TurnManager {

  private final PriorityQueue<EndCondition> endConditions;
  private final TurnCriteria turn;
  private final GamePlayers players;
  private String link;
  private Map<Integer, Double> endResult;

  public LocalTurnManager(GamePlayers players, TurnCriteria turn,
      Collection<EndCondition> conditions) {
    this.players = players;
    this.turn = turn;
    endConditions = new PriorityQueue<>(conditions);
    endResult = new HashMap<>();
    link = "";
  }

  /**
   * Creates a new turn manager with the same data as the turn manager passed in.
   *
   * @param turnManagerData the turn manager data to copy
   */
  public LocalTurnManager(TurnManagerData turnManagerData) {
    this(turnManagerData.players(), turnManagerData.turn(), turnManagerData.conditions(),
        turnManagerData.link());
  }

  public LocalTurnManager(GamePlayers players, TurnCriteria turnCriteria,
      Collection<EndCondition> endConditions, String link) {
    this.players = players;
    this.turn = turnCriteria;
    this.endConditions = new PriorityQueue<>(endConditions);
    this.link = link;
  }

  /**
   * Updates the turn manager with the current board.
   *
   * @return the next player's turn.
   */
  @Override
  public int incrementTurn() {
    return turn.incrementTurn();
  }

  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  @Override
  public int getCurrentPlayer() {
    return turn.getCurrentPlayer();
  }


  /**
   * Checks all endConditions and
   *
   * @return if the game is over
   */
  @Override
  public boolean isGameOver(ChessBoard board) {
    for (EndCondition ec : endConditions) {
      Map<Integer, Double> result = ec.getScores(board);
      if (!result.isEmpty()) {
        endResult = result;
        return true;
      }
    }
    return false;
  }

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @return scores of all players after game over.
   */
  @Override
  public Map<Integer, Double> getScores() {
    return Collections.unmodifiableMap(endResult);
  }

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  @Override
  public GamePlayers getGamePlayers() {
    return players;
  }

  /**
   * Gets the turn criteria for the game
   *
   * @return the turn criteria for the game
   */
  @Override
  public TurnCriteria getTurnCriteria() {
    return turn;
  }

  /**
   * Gets the end conditions for the game
   *
   * @return the end conditions for the game
   */
  @Override
  public Collection<EndCondition> getEndConditions() {
    return endConditions;
  }

  /**
   * Gets the turn manager API link for the current history manager data. Returns an empty string if
   * the history manager is a turn manager.
   *
   * @return the turn manager API link for the current turn manager data.
   */
  @Override
  public String getLink() {
    return link;
  }

  /**
   * Adds a link to the turn manager
   *
   * @param link the link to add
   */
  @Override
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Sets a callback to handle any sort of error that occurs during the game.
   *
   * @param errorHandler the error handler to set
   */
  @Override
  public void setErrorHandler(Consumer<Throwable> errorHandler) {
    // Do nothing
  }

}
