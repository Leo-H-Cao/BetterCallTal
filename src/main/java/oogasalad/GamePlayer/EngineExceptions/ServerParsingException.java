package oogasalad.GamePlayer.EngineExceptions;

public class ServerParsingException extends EngineException {

  private static final String MESSAGE = "Issues with serialization of server request or response";

  /**
   * Exceptions with the chess model
   */
  public ServerParsingException() {
    super(MESSAGE);
  }
}
