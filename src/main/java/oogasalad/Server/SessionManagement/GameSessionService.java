package oogasalad.Server.SessionManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component("activeSessions")
public class GameSessionService {

  private static final int INIT_CAP = 1;
  private final Map<String, GameSession> activeSessions = new HashMap<>(INIT_CAP);

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
   * @param sessionId the session id to add
   * @param session   the session to add
   */
  public void addSession(String sessionId, GameSession session) {
    activeSessions.put(sessionId, session);
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

