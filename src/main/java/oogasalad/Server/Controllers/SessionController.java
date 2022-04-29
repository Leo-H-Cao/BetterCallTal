package oogasalad.Server.Controllers;

import oogasalad.GamePlayer.Board.ChessBoard.ChessBoardData;
import oogasalad.Server.SessionManagement.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

  private final GameSessionService activeSessions;

  /**
   * Constructor called by Spring to create a new SessionController. Constructor initializes the
   * autowired
   *
   * @param activeSessions the game session service that is used to get the active game sessions.
   */
  @Autowired
  public SessionController(GameSessionService activeSessions) {
    this.activeSessions = activeSessions;
  }

  /**
   * Creates a new game session with the given id.
   *
   * @param id           the id of the game session
   * @param initialBoard the initial board of the game session
   */
  @PostMapping("/host/{id}/{host}/{opponent}")
  public void createGameSession(@PathVariable String id, @PathVariable int host,
      @PathVariable int opponent, @RequestBody ChessBoardData initialBoard) {
    activeSessions.addSession(id, host, opponent, initialBoard.toChessBoard());
  }

  /**
   * Joins the game session with the given id.
   *
   * @param id the id of the game session
   */
  @GetMapping("/join/{id}")
  public int joinGameSession(@PathVariable String id) {
    return activeSessions.getSession(id).getOpponent();
  }

  /**
   * Pauses the game session with the given id.
   *
   * @param id the id of the game session
   */
  @PutMapping("/pause/{id}")
  public void pauseGameSession(@PathVariable String id) {
    activeSessions.getSession(id).setPaused(true);
  }

  /**
   * Resumes the game session with the given id.
   *
   * @param id the id of the game session
   */
  @PutMapping("/resume/{id}")
  public void resumeGameSession(@PathVariable String id) {
    activeSessions.getSession(id).setPaused(false);
  }

  /**
   * Ends the game session with the given id.
   *
   * @param id the id of the game session
   */
  @DeleteMapping("/end/{id}")
  public void endGameSession(@PathVariable String id) {
    activeSessions.removeSession(id);
  }
}
