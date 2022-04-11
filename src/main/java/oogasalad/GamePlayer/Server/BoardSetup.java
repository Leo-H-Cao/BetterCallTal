package oogasalad.GamePlayer.Server;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardSetup {

  private static final Logger LOG = LogManager.getLogger(BoardSetup.class);

  private static final String BASIC_MOVEMENT_PACKAGE = "doc/GameEngineResources/BasicMovements/";
  private static final String JSON_EXTENSION = ".json";

  private static final String CUSTOM_MOVE_PACKAGE = "oogasalad.GamePlayer.Movement.CustomMovements.";
  private static final String TURN_CRITERIA_PACKAGE = "oogasalad.GamePlayer.Board.TurnCriteria.";
  private static final String END_CONDITION_PACKAGE = "oogasalad.GamePlayer.Board.EndConditions.";
  private static final String VALID_STATE_CHECKER_PACKAGE = "oogasalad.GamePlayer.ValidStateChecker.";
  private static final String MOVEMENT_MODIFIER_PACKAGE = "oogasalad.GamePlayer.Movement.MovementModifiers.";

  private ChessBoard myBoard;
  private JSONObject myJSONObject;

  //TODO: create custom exception for handling IOException
  public BoardSetup(String JSONFileName) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(JSONFileName)));
    myJSONObject = new JSONObject(content);
  }

  /***
   * @return ChessBoard object constructed from a JSON
   */
  public ChessBoard createBoard() throws IOException {
    int rows = Integer.parseInt(
        myJSONObject.getJSONArray("general").getJSONObject(0).get("rows").toString());
    int columns = Integer.parseInt(
        myJSONObject.getJSONArray("general").getJSONObject(0).get("columns").toString());

    Player[] players = getPlayers();
    myBoard = new ChessBoard(rows, columns, getTurnCriteria(players), players,
        getValidStateCheckers(), getEndConditions());
    setStartingPosition(myBoard);
    return myBoard;
  }

  /***
   * @return valid state checkers list as defined by the JSON
   */
  private List<ValidStateChecker> getValidStateCheckers() throws IOException {
    List<ValidStateChecker> validStateCheckers = new ArrayList<>();
    JSONArray validStateCheckerArray = myJSONObject.getJSONArray("general").getJSONObject(0)
        .getJSONArray("validStateCheckers");
    for (int i = 0; i < validStateCheckerArray.length(); i++) {
      validStateCheckers.add(
          (ValidStateChecker) createInstance(
              VALID_STATE_CHECKER_PACKAGE + validStateCheckerArray.getString(i), new Class[]{},
              new Object[]{}));
    }
    LOG.debug("Valid state checkers: " + validStateCheckers);
    return validStateCheckers;
  }

  /***
   * @return end conditions list as defined by the JSON
   */
  private List<EndCondition> getEndConditions() throws IOException {
    List<EndCondition> endConditions = new ArrayList<>();
    JSONArray endConditionArray = myJSONObject.getJSONArray("general").getJSONObject(0)
        .getJSONArray("endConditions");
    for (int i = 0; i < endConditionArray.length(); i++) {
      endConditions.add(
          (EndCondition) createInstance(END_CONDITION_PACKAGE + endConditionArray.getString(i),
              new Class[]{}, new Object[]{}));
    }
    LOG.debug("End conditions: " + endConditions);
    return endConditions;
  }

  /***
   * @return turn criteria as defined by the JSON
   */
  private TurnCriteria getTurnCriteria(Player[] players) throws IOException {
    TurnCriteria turnCriteria =  (TurnCriteria) createInstance(
        TURN_CRITERIA_PACKAGE + myJSONObject.getJSONArray("general").getJSONObject(0)
            .get("turnCriteria"), new Class[]{Player[].class}, new Object[]{players});
    LOG.debug("Turn criteria: " + turnCriteria);
    return turnCriteria;
  }

  /***
   * Creates an instance of the given class name
   *
   * @param className to instantiate
   * @return object of className
   */
  private Object createInstance(String className, Class<?>[] parameterTypes, Object[] parameters)
      throws IOException {
    try {
      Class<?> clazz = Class.forName(className);
      Constructor<?> constructor = clazz.getConstructor(parameterTypes);
      return constructor.newInstance(parameters);
    } catch (Error | Exception e) {
      LOG.debug("Class creation failed: " + className);
      throw new IOException(String.format("Class parsing failed: %s", className));
    }
  }

  /***
   * @return players as defined by the JSON
   */
  private Player[] getPlayers() {
    JSONArray rawData = myJSONObject.getJSONArray("playerInfo");
    Player[] players = new Player[rawData.length()];
    for (int i = 0; i < players.length; i++) {
      JSONArray opponents = rawData.getJSONObject(i).getJSONArray("opponents");
      int[] opponentArray = new int[opponents.length()];
      for (int j = 0; j < opponents.length(); j++) {
        opponentArray[j] = opponents.getInt(j);
      }
      players[i] = new Player(i, opponentArray);
    }
    LOG.debug("Players: " + Arrays.toString(players));
    return players;
  }

  /***
   * @return List of movements defined by the coordinates in a given JSON file
   * @throws IOException if error with reading
   */
  private List<MovementInterface> parseMovementFile(String JSONFileName) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(JSONFileName)));
    JSONObject movementFileObject = new JSONObject(content);
    JSONArray moves = movementFileObject.getJSONArray("moves");
    List<MovementInterface> movements = new ArrayList<>();

    for (int i = 0; i < moves.length(); i++) {
      JSONObject currentMove = moves.getJSONObject(i);
      Coordinate currentCoordinate = parseCoord(
          moves.getJSONObject(i).getJSONArray("relativeCoords"));
      movements.add(new Movement(currentCoordinate, currentMove.getBoolean("infinite")));
    }
    LOG.debug("Movements in " + JSONFileName + ": " + movements);
    return movements;
  }

  /***
   * @param rawCoords to get coord object from
   * @return coord object based on JSON array
   */
  private Coordinate parseCoord(JSONArray rawCoords) {
    return new Coordinate(rawCoords.getInt(1), rawCoords.getInt(0));
  }

  /***
   * @param JSONKey to get movement files from
   * @return list of movements based on the file names provided by the list corresponding to JSONKey
   */
  private List<MovementInterface> getMoveList(String JSONKey, int pieceIndex) throws IOException {
    JSONArray movementFiles = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex)
        .getJSONArray(JSONKey);
    List<MovementInterface> movements = new ArrayList<>();
    for (int i = 0; i < movementFiles.length(); i++) {
      movements.addAll(
          parseMovementFile(BASIC_MOVEMENT_PACKAGE + movementFiles.getString(i) + JSON_EXTENSION));
    }
    LOG.debug(String.format("Movement list for piece %d in %s: %s", pieceIndex, JSONKey, movements));
    return movements;
  }

  /***
   * @return custom moves as defined by the JSON
   */
  private List<MovementInterface> getCustomMovements(int pieceIndex) throws IOException {
    List<MovementInterface> customMovements = new ArrayList<>();
    JSONArray customMoveArray = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex)
        .getJSONArray("customMoves");
    for (int i = 0; i < customMoveArray.length(); i++) {
      customMovements.add(
          (MovementInterface) createInstance(CUSTOM_MOVE_PACKAGE + customMoveArray.getString(i),
              new Class[]{}, new Object[]{}));
    }
    LOG.debug(String.format("Custom movement list for piece %d: %s", pieceIndex, customMovements));
    return customMovements;
  }

  /***
   * @param JSONKey to get movement files from
   * @return list of movements modifiers based on the file names provided by the list
   * corresponding to JSONKey
   */
  private List<MovementModifier> getMovementModifiers(String JSONKey, int pieceIndex)
      throws IOException {
    List<MovementModifier> movementModifiers = new ArrayList<>();
    JSONArray movementModifierArray = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex)
        .getJSONArray(JSONKey);
    for (int i = 0; i < movementModifierArray.length(); i++) {
      movementModifiers.add(
          (MovementModifier) createInstance(
              MOVEMENT_MODIFIER_PACKAGE + movementModifierArray.getString(i), new Class[]{},
              new Object[]{}));
    }
    LOG.debug(String.format("Custom movement list for piece %d: %s", pieceIndex, movementModifiers));
    return movementModifiers;
  }

  /***
   * Sets up pieces on the board
   *
   * @param board to set pieces on
   */
  private void setStartingPosition(ChessBoard board) throws IOException {
    JSONArray pieces = myJSONObject.getJSONArray("pieces");
    for (int i = 0; i < pieces.length(); i++) {
      JSONObject rawPieceData = pieces.getJSONObject(i);

      int startRow = rawPieceData.getInt("row");
      int startCol = rawPieceData.getInt("col");
      String name = rawPieceData.getString("pieceName");
      String imageFile = rawPieceData.getString("imgFile");
      Coordinate startingCoordinate = new Coordinate(startRow, startCol);
      int team = rawPieceData.getInt("team");
      int pointValue = rawPieceData.getInt("pointValue");
      boolean mainPiece = rawPieceData.getInt("mainPiece") == 1;

      List<MovementInterface> movements = getMoveList("basicMovements", i);
      List<MovementInterface> captures = getMoveList("basicCaptures", i);
      List<MovementInterface> customMovements = getCustomMovements(i);
      movements.addAll(customMovements);
      captures.addAll(customMovements);

      List<MovementModifier> movementModifiers = getMovementModifiers("movementModifiers", i);
      List<MovementModifier> onInteractionModifiers = getMovementModifiers("onInteractionModifier", i);

      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, mainPiece,
          movements, captures, movementModifiers, onInteractionModifiers, imageFile);

      Piece currentPiece = new Piece(pieceData);
      myBoard.placePiece(new Coordinate(startRow, startCol), currentPiece);
    }
  }
}
