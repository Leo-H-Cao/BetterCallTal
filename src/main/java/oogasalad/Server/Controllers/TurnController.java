package oogasalad.Server.Controllers;

import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;
import oogasalad.Server.SessionManagement.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turns")
public class TurnController {

  private final GameSessionService activeSessions;

  /**
   * Constructor called by Spring to create a new TurnController. Constructor initializes the
   * autowired
   *
   * @param activeSessions the game session service that is used to get the active game sessions.
   */
  @Autowired
  public TurnController(GameSessionService activeSessions) {
    this.activeSessions = activeSessions;
  }

  /**
   * Updates the turn manager with the current board.
   *
   * @param id the id of the game
   * @return the current player
   */
  @PutMapping("/increment/{id}")
  @ResponseBody
  public int incrementTurn(@PathVariable String id) {
    return activeSessions.getSession(id).turns().incrementTurn();
  }

  /**
   * Determines which player is currently playing
   *
   * @param id the id of the game
   * @return int player id
   */
  @GetMapping("/current/{id}")
  @ResponseBody
  public int getCurrentPlayer(@PathVariable String id) {
    return activeSessions.getSession(id).turns().getCurrentPlayer();
  }

  /**
   * Checks all endConditions and returns true if the game is over.
   *
   * @param id    the id of the game
   * @param board the board to check
   * @return true if the game is over, false otherwise
   */
  @GetMapping("/isGameOver/{id}")
  @ResponseBody
  public boolean isGameOver(@PathVariable String id, @RequestBody ChessBoard board) {
    return activeSessions.getSession(id).turns().isGameOver(board);
  }

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @param id the id of the game
   * @return scores of all players after game over.
   */
  @GetMapping("/getScores/{id}")
  @ResponseBody
  public Map<Integer, Double> getScores(@PathVariable String id) {
    return activeSessions.getSession(id).turns().getScores();
  }

  /**
   * Gets all the players playing in the game
   *
   * @param id the id of the game
   * @return all players playing in the game
   */
  @GetMapping("/getPlayers/{id}")
  @ResponseBody
  public GamePlayers getGamePlayers(@PathVariable String id) {
    return activeSessions.getSession(id).turns().getGamePlayers();
  }
}
