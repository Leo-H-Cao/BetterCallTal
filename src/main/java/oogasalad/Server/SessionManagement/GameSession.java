package oogasalad.Server.SessionManagement;

import java.util.Objects;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.History.HistoryManager;
import oogasalad.GamePlayer.Board.History.LocalHistoryManager;
import oogasalad.GamePlayer.Board.TurnManagement.LocalTurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;

public final class GameSession {

  private final String gameSessionId;
  private final TurnManager turns;
  private final HistoryManager history;
  private boolean isPaused;

  /**
   * Creates the game session with the given id and the given initial board. Manages all of the
   *
   * @param gameSessionId the id of the game session.
   * @param initialBoard  the initial board of the game, which includes the initial state and rules
   *                      of the game.
   */
  public GameSession(String gameSessionId, ChessBoard initialBoard) {
    this.gameSessionId = gameSessionId;
    this.turns = new LocalTurnManager(initialBoard.getTurnManagerData());
    this.history = new LocalHistoryManager();
    this.isPaused = false;
  }


  /**
   * Checks if the game session is equal to the given object.
   *
   * @param o the object to check if it is equal to the game session.
   * @return true if the game session is equal to the given object, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GameSession that)) {
      return false;
    }
    return Objects.equals(gameSessionId, that.gameSessionId);
  }

  /**
   * Gets the hash code of the game session. The hash code is the hash code of the game session id.
   *
   * @return the hash code of the game session.
   */
  @Override
  public int hashCode() {
    return Objects.hash(gameSessionId);
  }

  /**
   * Gets the id of the game session.
   *
   * @return the id of the game session.
   */
  public String gameSessionId() {
    return gameSessionId;
  }

  /**
   * Gets the turn manager of the game session.
   *
   * @return the turn manager of the game session.
   */
  public TurnManager turns() {
    return turns;
  }

  /**
   * Gets the history of the game session.
   *
   * @return the history of the game session.
   */
  public HistoryManager history() {
    return history;
  }

  /**
   * Checks if the game session is active.
   *
   * @return true if the game session is active, false otherwise.
   */
  public boolean isPaused() {
    return isPaused;
  }

  /**
   * Sets the game session to be active.
   *
   * @param isActive the new value of the active state of the game session.
   */
  public void setPaused(boolean isActive) {
    this.isPaused = isActive;
  }

  /**
   * Gets the string representation of the game session.
   *
   * @return the string representation of the game session.
   */
  @Override
  public String toString() {
    return "GameSession[" +
        "gameSessionId=" + gameSessionId + ", " +
        "turns=" + turns + ", " +
        "history=" + history + ", " +
        "isActive=" + isPaused + ']';
  }

}
