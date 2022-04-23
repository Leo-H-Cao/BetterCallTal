package oogasalad.GamePlayer.Server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import oogasalad.GamePlayer.Server.Exceptions.ServerConnectionException;

public class RequestBuilder {

  public static final String CONTENT_TYPE = "Content-Type";
  public static final String APPLICATION_JSON = "application/json";

  private RequestBuilder() {
    // Empty constructor for utility class with no instances allowed
  }

  /**
   * Builds a GET request to the game server
   *
   * @param uri the uri of the game server
   * @return the HttpRequest representing the GET request
   */
  public static HttpRequest get(String uri) {
    return createRequest(uri).GET().build();
  }

  /**
   * Builds a POST request to the game server with the given body
   *
   * @param uri  the uri of the game server
   * @param json the JSON to send to the game server
   * @return the HttpRequest representing the POST request
   */
  public static HttpRequest post(String uri, String json) {
    return createRequest(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
  }

  /**
   * Builds a PUT request to the game server with the given body
   *
   * @param uri  the uri of the game server
   * @param json the JSON to send to the game server
   * @return the HttpRequest representing the PUT request
   */
  public static HttpRequest put(String uri, String json) {
    return createRequest(uri).PUT(HttpRequest.BodyPublishers.ofString(json)).build();
  }


  /**
   * Builds a DELETE request to the game server
   *
   * @param uri the uri of the game server
   * @return the HttpRequest representing the DELETE request
   */
  public static HttpRequest delete(String uri) {
    return createRequest(uri).DELETE().build();
  }

  /**
   * Sends a synchronous request to the game server and returns the response
   *
   * @param request the request to send to the game server
   * @return the response from the game server
   * @throws ServerConnectionException if the request fails
   */
  public static HttpResponse<String> sendRequest(HttpRequest request)
      throws ServerConnectionException {
    HttpClient client = HttpClient.newHttpClient();
    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new ServerConnectionException("Could not connect to server", e);
    }
  }

  /**
   * Creates a HttpRequest with the given uri
   *
   * @param uri the uri of the game server
   * @return the HttpRequestBuilder with the given uri to continue building the request
   */
  private static HttpRequest.Builder createRequest(String uri) {
    return HttpRequest.newBuilder().uri(URI.create(uri)).header(CONTENT_TYPE, APPLICATION_JSON);
  }
}
