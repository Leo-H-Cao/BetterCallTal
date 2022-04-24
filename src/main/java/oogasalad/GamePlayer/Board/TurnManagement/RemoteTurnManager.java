package oogasalad.GamePlayer.Board.TurnManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.ServerParsingException;
import oogasalad.GamePlayer.Server.RequestBuilder;

public class RemoteTurnManager implements TurnManager {

  private static final String BASE_URL = "http://localhost:8080/turns";
  private static final String INCREMENT_TURN = BASE_URL + "/increment/%s";
  private static final String GET_CURRENT_PLAYER = BASE_URL + "/current/%s";
  private static final String IS_GAMEOVER = BASE_URL + "/isGameOver/%s";
  private static final String GET_SCORES = BASE_URL + "/getScores/%s";
  private static final String GET_PLAYERS = BASE_URL + "/getPlayers/%s";
  private static final String GET_END_CONDITIONS = BASE_URL + "/getEndConditions/%s";
  private static final String GET_TURN_CRITERIA = BASE_URL + "/getTurnCriteria/%s";
  private static final Logger logger = Logger.getLogger(RemoteTurnManager.class.getName());
  private static final ObjectMapper mapper = new ObjectMapper();
  private final String id;

  public RemoteTurnManager(String id) {
    this.id = id;
  }

  /**
   * Updates the turn manager with the current board.
   *
   * @return the current player
   */
  @Override
  public int incrementTurn() throws EngineException {
    String url = String.format(INCREMENT_TURN, id);
    String json = RequestBuilder.EMPTY_JSON;

    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.post(url, json));
    try {
      return mapper.readValue(response.body(), Integer.class);
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
    }
  }

  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  @Override
  public int getCurrentPlayer() throws EngineException {
    String url = String.format(GET_CURRENT_PLAYER, id);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
    try {
      return mapper.readValue(response.body(), Integer.class);
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
    }
  }

  /**
   * Checks all endConditions and returns true if the game is over.
   *
   * @param board the board to check
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver(ChessBoard board) throws EngineException {
    String url = String.format(IS_GAMEOVER, id);
    String json = RequestBuilder.EMPTY_JSON;
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.put(url, json));
    try {
      return mapper.readValue(response.body(), Boolean.class);
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
    }
  }

  /**
   * Gets an immutable map of scores of all players after game over. If game isn't over, an empty
   * map is returned.
   *
   * @return scores of all players after game over.
   */
  @Override
  public Map<Integer, Double> getScores() throws EngineException {
    String url = String.format(GET_SCORES, id);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
    try {
      return mapper.readValue(response.body(), new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
    }
  }

  /**
   * Gets all the players playing in the game
   *
   * @return all players playing in the game
   */
  @Override
  public GamePlayers getGamePlayers() throws EngineException {
    String url = String.format(GET_PLAYERS, id);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
    try {
      return mapper.readValue(response.body(), GamePlayers.class);
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
    }
  }

  /**
   * Gets the turn criteria for the game
   *
   * @return the turn criteria for the game
   */
  @Override
  public TurnCriteria getTurnCriteria() throws EngineException {
    String url = String.format(GET_TURN_CRITERIA, id);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
    try {
      return mapper.readValue(response.body(), TurnCriteria.class);
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
    }
  }

  /**
   * Gets the end conditions for the game
   *
   * @return the end conditions for the game
   */
  @Override
  public Collection<EndCondition> getEndConditions() throws EngineException {
    String url = String.format(GET_END_CONDITIONS, id);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
    try {
      return mapper.readValue(response.body(), new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      logger.severe(e.getMessage());
      throw new ServerParsingException();
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


  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (RemoteTurnManager) obj;
    return Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "RemoteTurnManager[" +
        "id=" + id + ']';
  }

}
