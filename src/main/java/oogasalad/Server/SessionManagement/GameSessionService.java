package oogasalad.Server.SessionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Server.RequestBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("activeSessions")
public class GameSessionService {

  private static final int INIT_CAP = 1;
  private final Map<String, GameSession> activeSessions = new HashMap<>(INIT_CAP);

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return RequestBuilder.objectMapperWithPTV();
  }

  /**
   * Gets the session with the given sessionId.
   *
   * @param sessionId the session id to get the session for
   * @return gets the session for the given session id
   */
  public GameSession getSession(String sessionId) {
    return activeSessions.get(sessionId);
  }

  /**
   * Adds a session to the active sessions.
   *
   * @param sessionId    the session id to add
   * @param initialBoard the initial board for the session
   */
  public void addSession(String sessionId, int host, int opponent, ChessBoard initialBoard) {
    activeSessions.put(sessionId, new GameSession(sessionId, host, opponent));
    activeSessions.get(sessionId).addInitialBoard(initialBoard);
  }

  /**
   * Removes the session with the given session id.
   *
   * @param sessionId the session id to remove
   */
  public void removeSession(String sessionId) {
    activeSessions.remove(sessionId);
  }
}

