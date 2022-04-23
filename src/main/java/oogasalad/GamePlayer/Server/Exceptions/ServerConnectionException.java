package oogasalad.GamePlayer.Server.Exceptions;

import java.io.IOException;

public class ServerConnectionException extends IOException {

  /**
   * Constructs an {@code ServerConnectionException} with the specified detail message and cause.
   *
   * @param message The detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method)
   * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()}
   *                method). (A null value is permitted, and indicates that the cause is nonexistent
   *                or unknown.)
   * @since 1.6
   */
  public ServerConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
