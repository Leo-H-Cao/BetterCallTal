package oogasalad.Server.Services;

public interface GameSessionManager {

  /**
   * Creates a new game session with the given id.
   *
   * @param id the id of the game session
   */
  void createGameSession(String id);

  /**
   * Joins the game session with the given id.
   *
   * @param id the id of the game session
   */
  void joinGameSession(String id);

  /**
   * Pauses the game session with the given id.
   *
   * @param id the id of the game session
   */
  void pauseGameSession(String id);

  /**
   * Resumes the game session with the given id.
   *
   * @param id the id of the game session
   */
  void resumeGameSession(String id);

  /**
   * Ends the game session with the given id.
   *
   * @param id the id of the game session
   */
  void endGameSession(String id);

}
