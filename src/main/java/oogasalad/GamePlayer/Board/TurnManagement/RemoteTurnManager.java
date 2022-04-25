package oogasalad.GamePlayer.Board.TurnManagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Server.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoteTurnManager implements TurnManager {

  private static final Logger LOG = LogManager.getLogger(RemoteTurnManager.class);
  private static final String BASE_URL = "http://localhost:8080/turns";
  private static final String INCREMENT_TURN = BASE_URL + "/increment/%s";
  private static final String GET_CURRENT_PLAYER = BASE_URL + "/current/%s";
  private static final String IS_GAMEOVER = BASE_URL + "/isGameOver/%s";
  private static final String GET_SCORES = BASE_URL + "/getScores/%s";
  private static final String GET_PLAYERS = BASE_URL + "/getPlayers/%s";
  private static final String GET_END_CONDITIONS = BASE_URL + "/getEndConditions/%s";
  private static final String GET_TURN_CRITERIA = BASE_URL + "/getTurnCriteria/%s";
  private static final ObjectMapper mapper = RequestBuilder.objectMapperWithPTV();
  private final String id;
  private Consumer<Throwable> showAsyncError;

  public RemoteTurnManager(String id) {
    this.id = id;
    this.showAsyncError = (Throwable e) -> LOG.error(e.getMessage());
  }

  /**
   * Updates the turn manager with the current board.
   *
   * @return the current player
   */
  @Override
  public int incrementTurn() {
    String url = String.format(INCREMENT_TURN, id);
    String json = RequestBuilder.EMPTY_JSON;
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.post(url, json));
      return mapper.readValue(response.body(), Integer.class);
    } catch (Exception e) {
      handleError(e);
      return -1;
    }
  }

  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  @Override
  public int getCurrentPlayer() {
    String url = String.format(GET_CURRENT_PLAYER, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), Integer.class);
    } catch (Exception e) {
      handleError(e);
      return -1;
    }
  }

  /**
   * Checks all endConditions and returns true if the game is over.
   *
   * @param board the board to check
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver(ChessBoard board) {
    String url = String.format(IS_GAMEOVER, id);
    String json = RequestBuilder.EMPTY_JSON;
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.put(url, json));
      return mapper.readValue(response.body(), Boolean.class);
    } catch (Exception e) {
      handleError(e);
      return false;
    }
  }

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @return scores of all players after game over.
   */
  @Override
  public Map<Integer, Double> getScores() {
    String url = String.format(GET_SCORES, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), new TypeReference<>() {
      });
    } catch (Exception e) {
      handleError(e);
      return Map.of();
    }
  }

  /**
   * Gets all the players playing in the game
   *
   * @return all players playing in the game
   */
  @Override
  public GamePlayers getGamePlayers() {
    String url = String.format(GET_PLAYERS, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), GamePlayers.class);
    } catch (Exception e) {
      handleError(e);
      return new GamePlayers();
    }
  }

  /**
   * Gets the turn criteria for the game
   *
   * @return the turn criteria for the game
   */
  @Override
  public TurnCriteria getTurnCriteria() {
    String url = String.format(GET_TURN_CRITERIA, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), TurnCriteria.class);
    } catch (Exception e) {
      handleError(e);
      return new Linear(new Player[]{});
    }
  }

  /**
   * Gets the end conditions for the game
   *
   * @return the end conditions for the game
   */
  @Override
  public Collection<EndCondition> getEndConditions() {
    String url = String.format(GET_END_CONDITIONS, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), new TypeReference<>() {
      });
    } catch (Exception e) {
      handleError(e);
      return new ArrayList<>();
    }
  }

  /**
   * Gets the turn manager API link for the current history manager data. Returns an empty string if
   * the history manager is a turn manager.
   *
   * @return the turn manager API link for the current turn manager data.
   */
  @Override
  public String getLink() {
    return id;
  }

  /**
   * Sets a callback to handle any sort of error that occurs during the game.
   *
   * @param errorHandler the error handler to set
   */
  @Override
  public void setErrorHandler(Consumer<Throwable> errorHandler) {
    this.showAsyncError = errorHandler;
  }

  /**
   * Appropriately logs and (if connected to frontend) displays an error to the user.
   *
   * @param e the error to handle
   */
  private void handleError(Exception e) {
    LOG.error(e.getMessage());
    showAsyncError.accept(e);
  }

}
