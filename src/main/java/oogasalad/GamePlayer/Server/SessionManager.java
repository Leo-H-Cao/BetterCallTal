package oogasalad.GamePlayer.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.ServerParsingException;


public class SessionManager {

  private static final String BASE_URL = "http://localhost:8080/session";
  private static final String CREATE = BASE_URL + "/host/%s/%d/%d";
  private static final String JOIN = BASE_URL + "/join/%s";
  private static final String PAUSE = BASE_URL + "/pause/%s";
  private static final String RESUME = BASE_URL + "/resume/%s";
  private static final String END = BASE_URL + "/end/%s";
  private final ObjectMapper mapperJSON = new ObjectMapper();

  /**
   * Creates a new game session with the given id.
   *
   * @param id           the id of the game session
   * @param initialBoard the initial board of the game session
   */
  public void createGameSession(String id, int host, int opponent, ChessBoard initialBoard)
      throws EngineException {
    String uri = String.format(CREATE, id, host, opponent);
    try {
      String json = mapperJSON.writeValueAsString(initialBoard.getBoardData());
      RequestBuilder.sendRequest(RequestBuilder.post(uri, json));
    } catch (JsonProcessingException e) {
      throw new ServerParsingException();
    }
  }

  /**
   * Joins the game session with the given id.
   *
   * @param id the id of the game session
   */
  public int joinGameSession(String id) throws EngineException {
    String uri = String.format(JOIN, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(uri));
      return mapperJSON.readValue(response.body(), Integer.class);
    } catch (JsonProcessingException e) {
      throw new ServerParsingException();
    }
  }

  /**
   * Pauses the game session with the given id.
   *
   * @param id the id of the game session
   */
  public void pauseGameSession(String id) throws EngineException {
    String uri = String.format(PAUSE, id);
    String json = RequestBuilder.EMPTY_JSON;
    RequestBuilder.sendRequest(RequestBuilder.put(uri, json));
  }

  /**
   * Resumes the game session with the given id.
   *
   * @param id the id of the game session
   */
  public void resumeGameSession(String id) throws EngineException {
    String uri = String.format(RESUME, id);
    String json = RequestBuilder.EMPTY_JSON;
    RequestBuilder.sendRequest(RequestBuilder.put(uri, json));
  }

  /**
   * Ends the game session with the given id.
   *
   * @param id the id of the game session
   */
  public void endGameSession(String id) throws EngineException {
    String uri = String.format(END, id);
    RequestBuilder.sendRequest(RequestBuilder.delete(uri));
  }


}
