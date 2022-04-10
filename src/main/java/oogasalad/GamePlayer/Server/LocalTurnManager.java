package oogasalad.GamePlayer.Server;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.GamePlayers;
import oogasalad.GamePlayer.Board.Player;
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
  private final ChessHistory history;
  private Map<Integer, Double> endResult;

  //TO-DO: implement a builder pattern for this class once class finalization is complete
  public LocalTurnManager(ChessHistory history, GamePlayers players, TurnCriteria turn,
      EndCondition... conditions) {
    this(history, players, turn, Arrays.asList(conditions));
  }

  public LocalTurnManager(ChessHistory history, GamePlayers players, TurnCriteria turn,
      Iterable<EndCondition> conditions) {
    this.history = history;
    this.players = players;
    this.turn = turn;
    endConditions = new PriorityQueue<>();
    for (EndCondition condition : conditions) {
      endConditions.add(condition);
    }
    endResult = new HashMap<>();
  }

  /**
   * Adds a new EndCondition to the end of the priority of EndConditions to check.
   *
   * @param condition the EndCondition to add
   */
  @Override
  public void addEndCondition(EndCondition condition) {
    endConditions.add(condition);
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
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  @Override
  public ChessBoard getCurrentBoard() {
    return history.getCurrentState();
  }

  /**
   * Adds a new state to the history.
   *
   * @param board The new state to add.
   */
  @Override
  public ChessBoard addToHistory(ChessBoard board) {
    return history.add(board);
  }

  /**
   * Returns the size of the history.
   *
   * @return the size of the history.
   */
  @Override
  public int getHistorySize() {
    return history.size();
  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  @Override
  public boolean isHistoryEmpty() {
    return history.isEmpty();
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
   * Gets the player object with the associated ID
   *
   * @param id of player
   * @return player with given id
   */
  @Override
  public Player getPlayer(int id) {
    return players.getPlayer(id);
  }

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  @Override
  public Player[] getPlayers() {
    return players.getPlayers();
  }

  /**
   * Gets all team numbers
   *
   * @return team numbers for all players
   */
  @Override
  public int[] getTeams() {
    return players.getTeams();
  }


}
