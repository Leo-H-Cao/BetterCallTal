package oogasalad.Server.Controllers;

import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.History.History;
import oogasalad.Server.SessionManagement.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class HistoryController {

  private final GameSessionService activeSessions;

  /**
   * Constructor called by Spring to create a new HistoryController. Constructor initializes the
   * autowired
   *
   * @param activeSessions the game session service that is used to get the active game sessions.
   */
  @Autowired
  public HistoryController(GameSessionService activeSessions) {
    this.activeSessions = activeSessions;
  }

  /**
   * Gets the current chess board state of the game.
   *
   * @param id The id of the game.
   * @return the current chess board state of the game.
   */
  public ChessBoard getCurrentBoard(String id) {
    return activeSessions.getSession(id).history().getCurrentBoard();
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param id       The id of the game.
   * @param newState The new state to add.
   */
  public History add(String id, History newState) {
    return activeSessions.getSession(id).history().add(newState);
  }

  /**
   * Returns the most recent state.
   *
   * @param id The id of the game.
   * @return the most recent state.
   */
  public History getLast(String id) {
    return activeSessions.getSession(id).history().getLast();
  }

  /**
   * Returns the size of the history.
   * <p>NOTE: Do not simply use the size of the history list to determine the current index of the
   * history. Assuming that an undo operation is performed, the current index will be less than the
   * last index of the history.
   *
   * @param id The id of the game.
   * @return the size of the history.
   */
  public int size(String id) {
    return activeSessions.getSession(id).history().size();
  }

  /**
   * Returns the state at a given index.
   *
   * @param id    The id of the game.
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  public History get(String id, int index) {
    return activeSessions.getSession(id).history().get(index);
  }

  /**
   * Returns the starting board configuration.
   *
   * @param id The id of the game.
   * @return the starting board configuration.
   */
  public History getFirst(String id) {
    return activeSessions.getSession(id).history().getFirst();
  }

  /**
   * Returns the current state of the game.
   *
   * @param id The id of the game.
   * @return the current state of the game.
   */
  public History getCurrent(String id) {
    return activeSessions.getSession(id).history().getCurrent();
  }

  /**
   * Returns the index of the most recent state.
   *
   * @param id The id of the game.
   * @return the index of the most recent state.
   */
  public int getCurrentIndex(String id) {
    return activeSessions.getSession(id).history().getCurrentIndex();
  }

  /**
   * Rewinds the history to a certain index.
   *
   * @param id    The id of the game.
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  public History goToState(String id, int index) {
    return activeSessions.getSession(id).history().goToState(index);
  }

  /**
   * Clears the history.
   *
   * @param id The id of the game.
   */
  public void clearHistory(String id) {
    activeSessions.getSession(id).history().clearHistory();
  }

  /**
   * Returns whether the history is empty.
   *
   * @param id The id of the game.
   * @return true if the history is empty, false otherwise.
   */
  public boolean isEmpty(String id) {
    return activeSessions.getSession(id).history().isEmpty();
  }

  /**
   * @param id The id of the game.
   * @return way to stream over the history
   */
  public Stream<History> stream(String id) {
    return activeSessions.getSession(id).history().stream();
  }
}
