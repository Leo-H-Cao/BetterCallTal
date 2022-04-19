package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.CustomMovements.EnPassant;
import oogasalad.GamePlayer.ValidStateChecker.BankBlocker;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InARow implements EndCondition {

  private static final Logger LOG = LogManager.getLogger(InARow.class);
  public static final int[] DIRECTIONS = new int[]{-1, 0, 1};

  public static final String IAR_CONFIG_FILE_HEADER = "doc/GameEngineResources/Other/";
  public static final String IAR_DEFAULT_FILE = "InARowThree";

  private static final int IAR_DEFAULT_VAL = 3;
  private static final List<String> IAR_DEFAULT_NAMES = List.of("checker");

  private List<String> pieceNames;
  private int numInARow;

  /***
   * Constructs InARow with default file
   */
  public InARow() {
    this(IAR_DEFAULT_FILE);
  }

  /***
   * Constructs InARow with provided config file
   *
   * @param configFile to read
   */
  public InARow(String configFile) {
    LOG.debug(String.format("Config file: %s", configFile));
    try {
      String content = new String(Files.readAllBytes(
          Path.of(IAR_CONFIG_FILE_HEADER + configFile + JSON_EXTENSION)));
      JSONObject data = new JSONObject(content);

      numInARow = data.getInt("num");
      pieceNames = new ArrayList<>();
      JSONArray pieceArray = data.getJSONArray("pieces");
      for(int i=0; i<pieceArray.length(); i++) {
        pieceNames.add(pieceArray.getString(i).toLowerCase());
      }
    } catch (IOException e) {
      numInARow = IAR_DEFAULT_VAL;
      pieceNames = IAR_DEFAULT_NAMES;
    }
  }

  /***
   * Returns win for
   *
   * @param board to check
   * @return above
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    Map<Integer, Double> scores = new HashMap<>();
    board.stream().flatMap(List::stream).toList().stream().filter(t ->
        t.getPiece().isPresent() &&
        pieceNames.contains(t.getPiece().get().getName().toLowerCase()) &&
        anyRayReachesNum(board, t.getCoordinates(),
            t.getPiece().get().getName(), t.getPiece().get().getTeam())).forEach(t -> {
          scores.put(t.getPiece().get().getTeam(), WIN);
          Arrays.stream(board.getPlayer(t.getPiece().get().getTeam()).opponentIDs()).filter(o ->
              !scores.containsKey(o)).forEach(o -> scores.put(o, LOSS));
        });

    LOG.debug(String.format("Scores: %s", scores));

    if(scores.isEmpty()) return scores;

    Arrays.stream(board.getPlayers()).filter(p -> !scores.containsKey(p.teamID())).forEach(p ->
        scores.put(p.teamID(), DRAW));

    return scores;
  }

  /***
   * Extends a ray in all directions over the chessboard and returns if one of those rays
   * reaches the desired length
   * @param board to extend the rays on
   * @param start of current piece
   * @param pieceName that the rays must match
   * @param pieceTeam is the team of the piece
   * @return above
   */
  private boolean anyRayReachesNum(ChessBoard board, Coordinate start,
    String pieceName, int pieceTeam) {
    return Arrays.stream(DIRECTIONS).anyMatch(i -> Arrays.stream(DIRECTIONS).anyMatch(j ->
        rayReachesNum(board, pieceName, pieceTeam, start, Coordinate.of(i, j), numInARow)));
  }

  /***
   * Finds if a ray extending in a given direction on the board reaches the desired length
   * False is returned if direction is 0 because that is not a ray
   *
   * @param board to extend the ray on
   * @param pieceName that the ray must match
   * @param pieceTeam is the team of the given piece
   * @param coordinate of current piece
   * @param direction to move in
   * @param rayLength is the length the ray must reach
   * @return above
   */
  private boolean rayReachesNum(ChessBoard board, String pieceName, int pieceTeam,
      Coordinate coordinate, Coordinate direction, int rayLength) {
    if(rayLength == 0) return true;
    if(direction.getCol() == 0 && direction.getRow() == 0 ||
        !continueExtendingRay(board,coordinate, pieceName, pieceTeam)) return false;

    LOG.debug(String.format("Current coordinate and rayLength: (%d, %d), %d",
        coordinate.getRow(), coordinate.getCol(), rayLength));
    Coordinate newCoordinate = Coordinate.add(coordinate, direction);

    return rayReachesNum(board, pieceName, pieceTeam,
        newCoordinate, direction, rayLength - 1);
  }

  /***
   * Checks to see whether the ray can continue to extend based on board boundaries and piece name
   *
   * @param board to check
   * @param coordinate to extend to
   * @param pieceName of reference piece
   * @param pieceTeam of reference piece
   * @return above
   */
  private boolean continueExtendingRay(ChessBoard board, Coordinate coordinate,
      String pieceName, int pieceTeam) {
    try {
      if (board.getTile(coordinate).getPiece().isPresent() &&
          board.getTile(coordinate).getPiece().get().getName().equalsIgnoreCase(pieceName) &&
          board.getTile(coordinate).getPiece().get().getTeam() == pieceTeam)
        return true;
    } catch (OutsideOfBoardException e) {
      return false;
    }
    return false;
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
