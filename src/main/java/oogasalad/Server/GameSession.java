package oogasalad.Server;

import java.util.Objects;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.Server.Managers.HistoryManager;
import oogasalad.GamePlayer.Board.History.LocalHistoryManager;
import oogasalad.GamePlayer.Board.TurnManagement.LocalTurnManager;
import oogasalad.Server.Managers.TurnManager;

public record GameSession(String gameSessionId, TurnManager turns, HistoryManager history) {

  /**
   * Creates the game session with the given id and the given initial board. Manages all of the
   *
   * @param gameSessionId the id of the game session.
   * @param initialBoard  the initial board of the game, which includes the initial state and rules
   *                      of the game.
   */
  public GameSession(String gameSessionId, ChessBoard initialBoard) {
    this(gameSessionId, new LocalTurnManager(initialBoard.getTurnManagerData()),
        new LocalHistoryManager());
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
}
