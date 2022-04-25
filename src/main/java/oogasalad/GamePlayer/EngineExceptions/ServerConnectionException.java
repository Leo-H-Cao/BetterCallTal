package oogasalad.GamePlayer.EngineExceptions;

public class ServerConnectionException extends EngineException {

  private static final String MESSAGE = "Could not connect to server";

  /**
   * Exceptions with the chess model, specifically regarding the connection to the server.
   */
  public ServerConnectionException() {
    super(MESSAGE);
  }
}
