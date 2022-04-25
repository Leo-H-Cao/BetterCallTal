package oogasalad.GamePlayer.Board.History;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.function.Consumer;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessBoard.ChessBoardData;
import oogasalad.GamePlayer.Server.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RemoteHistoryManager implements HistoryManager {

  private static final Logger LOG = LogManager.getLogger(RemoteHistoryManager.class);
  private static final String BASE_URL = "http://localhost:8080/history";
  private static final String CURRENT_BOARD = BASE_URL + "/current-board/%s";
  private static final String ADD = BASE_URL + "/add/%s";
  private static final String SIZE = BASE_URL + "/size/%s";
  private static final String GET = BASE_URL + "/index/%s/%d";
  private static final String GET_FIRST = BASE_URL + "/first/%s";
  private static final String GET_LAST = BASE_URL + "/last/%s";
  private static final String GET_CURRENT = BASE_URL + "/current/%s";
  private static final String GET_CURRENT_INDEX = BASE_URL + "/current-index/%s";
  private static final String REWIND = BASE_URL + "/rewind/%s/%d";
  private static final String CLEAR = BASE_URL + "/clear/%s";
  private static final String IS_EMPTY = BASE_URL + "/empty/%s";
  private static final String STREAM = BASE_URL + "/stream/%s";
  private static final ObjectMapper mapper = RequestBuilder.objectMapperWithPTV();
  private final String id;
  private Consumer<Throwable> showAsyncError;

  public RemoteHistoryManager(String id) {
    this.id = id;
    this.showAsyncError = (Throwable e) -> LOG.error(e.getMessage());
  }

  /**
   * Gets the current chess board state of the game.
   *
   * @return the current chess board state of the game.
   */
  @Override
  public ChessBoard getCurrentBoard() {
    String url = String.format(CURRENT_BOARD, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), ChessBoardData.class).toChessBoard();
    } catch (Exception e) {
      handleError(e);
      return new ChessBoardData().toChessBoard();
    }
  }

  /**
   * Adds a new state to the history. Makes copy of state passed in to prevent mutation.
   *
   * @param newState The new state to add.
   */
  @Override
  public History add(History newState) {
    String url = String.format(ADD, id);
    try {
      String json = mapper.writeValueAsString(newState.getHistoryData());
      LOG.info(json);
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.post(url, json));
      LOG.info(response.body());
      return mapper.readValue(response.body(), HistoryData.class).toHistory();
    } catch (Exception e) {
      handleError(e);
      return new HistoryData().toHistory();
    }
  }

  /**
   * Returns the most recent state.
   *
   * @return the most recent state.
   */
  @Override
  public History getLast() {
    String url = String.format(GET_LAST, id);
    return getHistoryFromURI(url);
  }


  /**
   * Returns the size of the history.
   * <p>NOTE: Do not simply use the size of the history list to determine the current index of the
   * history. Assuming that an undo operation is performed, the current index will be less than the
   * last index of the history.
   *
   * @return the size of the history.
   */
  @Override
  public int size() {
    return getHistoryInteger(SIZE);
  }

  /**
   * Returns the state at a given index.
   *
   * @param index The index of the state to return.
   * @return the state at a given index.
   */
  @Override
  public History get(int index) {
    String url = String.format(GET, id, index);
    return getHistoryFromURI(url);
  }

  /**
   * Returns the starting board configuration.
   *
   * @return the starting board configuration.
   */
  @Override
  public History getFirst() {
    String url = String.format(GET_FIRST, id);
    return getHistoryFromURI(url);
  }

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game.
   */
  @Override
  public History getCurrent() {
    String url = String.format(GET_CURRENT, id);
    return getHistoryFromURI(url);
  }

  /**
   * Returns the index of the most recent state.
   *
   * @return the index of the most recent state.
   */
  @Override
  public int getCurrentIndex() {
    return getHistoryInteger(GET_CURRENT_INDEX);
  }

  /**
   * Rewinds the history to a certain index.
   *
   * @param index The index to rewind to.
   * @return the state that was rewound to.
   */
  @Override
  public History goToState(int index) {
    String url = String.format(REWIND, id, index);
    String json = RequestBuilder.EMPTY_JSON;
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.put(url, json));
      return mapper.readValue(response.body(), HistoryData.class).toHistory();
    } catch (Exception e) {
      handleError(e);
      return new HistoryData().toHistory();
    }
  }

  /**
   * Clears the history.
   */
  @Override
  public void clearHistory() {
    String url = String.format(CLEAR, id);
    try {
      RequestBuilder.sendRequest(RequestBuilder.delete(url));
    } catch (Exception e) {
      handleError(e);
    }
  }

  /**
   * Returns whether the history is empty.
   *
   * @return true if the history is empty, false otherwise.
   */
  @Override
  public boolean isEmpty() {
    String url = String.format(IS_EMPTY, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), Boolean.class);
    } catch (Exception e) {
      handleError(e);
      return true;
    }
  }

  /**
   * Gets a stream of the history.
   *
   * @return way to stream over the history
   */
  @Override
  public Stream<History> stream() {
    String url = String.format(STREAM, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), new TypeReference<>() {
      });
    } catch (Exception e) {
      handleError(e);
      return Stream.empty();
    }
  }

  /**
   * Gets the history API link for the current history manager data. Returns an empty string if the
   * history manager is a local history manager.
   *
   * @return the history API link for the current history manager data.
   */
  @Override
  public String getLink() {
    return id;
  }

  /**
   * Gets the history manager data.
   *
   * @return the history manager data.
   */
  @Override
  public HistoryManagerData getHistoryManagerData() {
    return new HistoryManagerData(this);
  }

  /**
   * Sets a callback to handle any sort of error that occurs during the game.
   *
   * @param errorHandler the error handler to set
   */
  @Override
  public void setErrorHandler(Consumer<Throwable> errorHandler) {
    this.showAsyncError = errorHandler;
  }

  /**
   * Gets the history from the given URI.
   *
   * @param url the URI to get the history from.
   * @return the history from the given URI.
   */
  private History getHistoryFromURI(String url) {
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), HistoryData.class).toHistory();
    } catch (Exception e) {
      handleError(e);
      return new HistoryData().toHistory();
    }
  }

  /**
   * Gets an integer based request from the given URI.
   *
   * @param baseURI the base URI to get the integer from.
   * @return the integer from the given URI.
   */
  private int getHistoryInteger(String baseURI) {
    String url = String.format(baseURI, id);
    try {
      HttpResponse<String> response = RequestBuilder.sendRequest(RequestBuilder.get(url));
      return mapper.readValue(response.body(), Integer.class);
    } catch (Exception e) {
      handleError(e);
      return 0;
    }
  }

  /**
   * Appropriately logs and (if connected to frontend) displays an error to the user.
   *
   * @param e the error to handle
   */
  private void handleError(Exception e) {
    LOG.error(e.getMessage());
    showAsyncError.accept(e);
  }
}
