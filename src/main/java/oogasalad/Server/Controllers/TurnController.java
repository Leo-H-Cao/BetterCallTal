package oogasalad.Server.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessBoard.ChessBoardData;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
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

  private static final String EXCEPT_MSG = "Supposedly Unreachable Condition Reached";
  private static final Logger logger = Logger.getLogger(TurnController.class.getName());
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
    try {
      return activeSessions.getSession(id).turns().incrementTurn();
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return -1;
    }
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
    try {
      return activeSessions.getSession(id).turns().getCurrentPlayer();
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return -1;
    }
  }

  /**
   * Checks all endConditions and returns true if the game is over.
   *
   * @param id    the id of the game
   * @param board the board to check
   * @return true if the game is over, false otherwise
   */
  @PutMapping("/isGameOver/{id}")
  @ResponseBody
  public boolean isGameOver(@PathVariable String id, @RequestBody ChessBoardData board) {
    try {
      return activeSessions.getSession(id).turns().isGameOver(new ChessBoard(board));
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return false;
    }
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
    try {
      return activeSessions.getSession(id).turns().getScores();
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return Map.of();
    }
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
    try {
      return activeSessions.getSession(id).turns().getGamePlayers();
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return new GamePlayers();
    }
  }

  /**
   * Gets the turn criteria for the game
   *
   * @param id the id of the game
   * @return the turn criteria for the game
   */
  @GetMapping("/getTurnCriteria/{id}")
  @ResponseBody
  public TurnCriteria getTurnCriteria(@PathVariable String id) {
    try {
      return activeSessions.getSession(id).turns().getTurnCriteria();
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return new Linear(new Player[1]);
    }
  }

  /**
   * Gets the end conditions for the game
   *
   * @param id the id of the game
   * @return the end conditions for the game
   */
  @GetMapping("/getEndConditions/{id}")
  @ResponseBody
  public Collection<EndCondition> getEndConditions(@PathVariable String id) {
    try {
      return activeSessions.getSession(id).turns().getEndConditions();
    } catch (Exception e) {
      logger.log(java.util.logging.Level.SEVERE, EXCEPT_MSG, e);
      return new ArrayList<>();
    }
  }

}
