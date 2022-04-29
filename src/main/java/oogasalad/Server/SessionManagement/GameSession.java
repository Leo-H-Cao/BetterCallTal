package oogasalad.Server.SessionManagement;

import java.util.Objects;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.History.History;
import oogasalad.GamePlayer.Board.History.HistoryManager;
import oogasalad.GamePlayer.Board.History.LocalHistoryManager;
import oogasalad.GamePlayer.Board.TurnManagement.LocalTurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;

public final class GameSession {

  private final String gameSessionId;
  private final int host;
  private final int opponent;
  private TurnManager turns;
  private HistoryManager history;
  private boolean isPaused;

  /**
   * Creates the game session with the given id and the given initial board. Manages all of the
   *
   * @param gameSessionId the id of the game session.
   */
  public GameSession(String gameSessionId, int host, int opponent) {
    this.gameSessionId = gameSessionId;
    this.host = host;
    this.opponent = opponent;
    this.isPaused = false;
  }

  /**
   * Adds the initial board to the game session.
   *
   * @param initialBoard the board to link the turn manager to.
   */
  public void addInitialBoard(ChessBoard initialBoard) {
    ChessBoard board = initialBoard.toServerChessBoard(gameSessionId, host);
    this.turns = new LocalTurnManager(board.getTurnManagerData());
    this.history = new LocalHistoryManager(History.fromBoard(board));
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
   * Gets the host of the game session.
   *
   * @return the host of the game session.
   */
  public int getHost() {
    return host;
  }

  /**
   * Gets the opponent of the game session.
   *
   * @return the opponent of the game session.
   */
  public int getOpponent() {
    return opponent;
  }
}
