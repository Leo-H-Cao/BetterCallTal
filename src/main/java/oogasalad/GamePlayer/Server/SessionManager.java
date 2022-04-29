package oogasalad.GamePlayer.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SessionManager {

  private static final String BASE_URL = "http://localhost:8080/session";
  private static final String CREATE = BASE_URL + "/host/%s/%d/%d";
  private static final String JOIN = BASE_URL + "/join/%s";
  private static final String PAUSE = BASE_URL + "/pause/%s";
  private static final String RESUME = BASE_URL + "/resume/%s";
  private static final String END = BASE_URL + "/end/%s";
  private static final Logger LOG = LogManager.getLogger(SessionManager.class);
  private final ObjectMapper mapperJSON = RequestBuilder.objectMapperWithPTV();

  /**
   * Creates a new game session with the given id.
   *
   * @param id           the id of the game session
   * @param initialBoard the initial board of the game session
   */
  public void createGameSession(String id, int host, int opponent, ChessBoard initialBoard)
      throws EngineException, JsonProcessingException {
    String uri = String.format(CREATE, id, host, opponent);
    String json = mapperJSON.writeValueAsString(initialBoard.getBoardData());
    LOG.info(json);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.post(uri, json));
    LOG.info(response.statusCode());
    LOG.info(response.body());
  }

  /**
   * Joins the game session with the given id.
   *
   * @param id the id of the game session
   */
  public int joinGameSession(String id) throws EngineException, JsonProcessingException {
    String uri = String.format(JOIN, id);
    HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(uri));
    return Integer.parseInt(response.body());

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
