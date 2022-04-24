package oogasalad.GamePlayer.Server;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.ServerConnectionException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class RequestBuilder {

  public static final String EMPTY_JSON = "{}";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";

  private RequestBuilder() {
    // Empty constructor for utility class with no instances allowed
  }

  public static ObjectMapper objectMapperWithPTV() {
    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfSubTypeIsArray()
        .allowIfSubType(ChessTile.class)
        .allowIfSubType(Coordinate.class)
        .allowIfSubType(ArrayList.class)
        .allowIfSubType(Piece.class)
        .build();
    return new ObjectMapper().activateDefaultTyping(ptv, DefaultTyping.NON_FINAL)
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
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
      throws EngineException {
    HttpClient client = HttpClient.newHttpClient();
    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new ServerConnectionException();
    }
  }

  /**
   * Creates a HttpRequest with the given uri
   *
   * @param uri the uri of the game server
   * @return the HttpRequestBuilder with the given uri to continue building the request
   */
  private static HttpRequest.Builder createRequest(String uri) {
    return HttpRequest.newBuilder().uri(URI.create(uri)).setHeader(CONTENT_TYPE, APPLICATION_JSON);
  }
}
