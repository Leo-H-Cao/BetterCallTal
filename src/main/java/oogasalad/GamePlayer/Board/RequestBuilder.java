package oogasalad.GamePlayer.Board;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
   * @return the HttpResponse from the game server
   */
  public static HttpResponse<String> get(String uri) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = createRequest(uri).GET().build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Builds a POST request to the game server with the given body
   *
   * @param uri  the uri of the game server
   * @param json the JSON to send to the game server
   * @return the HttpResponse from the game server
   */
  public static HttpResponse<String> post(String uri, String json)
      throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = createRequest(uri).POST(HttpRequest.BodyPublishers.ofString(json))
        .build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Builds a PUT request to the game server with the given body
   *
   * @param uri  the uri of the game server
   * @param json the JSON to send to the game server
   * @return the HttpResponse from the game server
   */
  public static HttpResponse<String> put(String uri, String json)
      throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = createRequest(uri).PUT(HttpRequest.BodyPublishers.ofString(json)).build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Builds a DELETE request to the game server
   *
   * @param uri the uri of the game server
   * @return the HttpResponse from the game server
   */
  public static HttpResponse<String> delete(String uri) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = createRequest(uri).DELETE().build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
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
