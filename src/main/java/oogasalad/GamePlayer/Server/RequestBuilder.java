package oogasalad.GamePlayer.Server;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard.ChessBoardData;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.TileAction;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.ServerConnectionException;
import oogasalad.GamePlayer.GamePiece.MovementHandler;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.GamePiece.SupplementaryPieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;

public class RequestBuilder {

  public static final String EMPTY_JSON = "{}";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";

  private RequestBuilder() {
    // Empty constructor for utility class with no instances allowed
  }

  public static ObjectMapper objectMapperWithPTV() {
    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfBaseType(TurnCriteria.class)
        .allowIfBaseType(EndCondition.class)
        .allowIfSubTypeIsArray()
        .allowIfBaseType(ChessTile.class)
        .allowIfBaseType(TileAction.class)
        .allowIfBaseType(EngineException.class)
        .allowIfBaseType(MovementInterface.class)
        .allowIfBaseType(Piece.class)
        .allowIfBaseType(PieceData.class)
        .allowIfBaseType(SupplementaryPieceData.class)
        .allowIfBaseType(MovementHandler.class)
        .allowIfBaseType(MovementModifier.class)
        .allowIfBaseType(FileReader.class)
        .allowIfBaseType(ValidStateChecker.class)
        .allowIfBaseType(Coordinate.class)
        .allowIfBaseType(Collection.class)
        .allowIfBaseType(Collections.emptyList().getClass())
        .allowIfBaseType("java.util.ImmutableCollections$List12")
        .allowIfBaseType(List.of(new Coordinate(0, 0)).getClass())
        .allowIfBaseType("java.util.ImmutableCollections$ListN")
        .build();
    return new ObjectMapper().activateDefaultTyping(ptv, DefaultTyping.NON_FINAL)
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
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
