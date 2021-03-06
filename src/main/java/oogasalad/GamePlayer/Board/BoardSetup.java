package oogasalad.GamePlayer.Board;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.History.HistoryManager;
import oogasalad.GamePlayer.Board.History.RemoteHistoryManager;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.TileAction;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.GamePiece.PieceJSONData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;
import oogasalad.GamePlayer.Server.SessionManager;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * Sets up a board given a JSON file
 *
 * @author Jed, Vincent, Ritvik
 */
public class BoardSetup {

  private static final Logger LOG = LogManager.getLogger(BoardSetup.class);
  private static final String BASIC_MOVEMENT_PACKAGE = "doc/GameEngineResources/BasicMovements/";
  private static final String PIECE_JSON_PACKAGE = "doc/GameEngineResources/Pieces/";
  public static final String JSON_EXTENSION = ".json";

  private static final String TILE_ACTION_PACKAGE = "oogasalad.GamePlayer.Board.Tiles.CustomTiles.";
  private static final String CUSTOM_MOVE_PACKAGE = "oogasalad.GamePlayer.Movement.CustomMovements.";
  private static final String TURN_CRITERIA_PACKAGE = "oogasalad.GamePlayer.Board.TurnCriteria.";
  private static final String END_CONDITION_PACKAGE = "oogasalad.GamePlayer.Board.EndConditions.";
  private static final String VALID_STATE_CHECKER_PACKAGE = "oogasalad.GamePlayer.ValidStateChecker.";
  private static final String MOVEMENT_MODIFIER_PACKAGE = "oogasalad.GamePlayer.Movement.MovementModifiers.";

  /***
   * Private constructor because utility class
   */
  private BoardSetup() {
    // Private because utility class
  }

  /**
   * Creates a ChessBoard from a JSON file for a local game.
   *
   * @param path the path to the JSON file
   * @return the ChessBoard
   * @throws IOException if the file is not found
   */
  public static ChessBoard createLocalBoard(String path) throws IOException {
    return boardFromJSONPath(path);
  }

  /**
   * Creates a JSONObject from a JSON file for
   *
   * @param path       the path to the JSON file
   * @param key        the key to the game trying to join
   * @param thisPlayer the player trying to join
   * @return the ChessBoard
   * @throws IOException if the file is not found
   */
  public static ChessBoard createRemoteBoard(String path, String key, int thisPlayer)
      throws IOException, EngineException {
    ChessBoard localboard = createLocalBoard(path);
    int otherPlayer = 1 - thisPlayer;
    SessionManager sessionManager = new SessionManager();
    sessionManager.createGameSession(key, thisPlayer, otherPlayer, localboard);
    return localboard.toServerChessBoard(key, thisPlayer);
  }

  /**
   * Creates a ChessBoard from the server's JSON file
   *
   * @param key the key to the game trying to join
   * @return the ChessBoard
   */
  public static ChessBoard joinRemoteBoard(String key)
      throws EngineException, JsonProcessingException {
    SessionManager sessionManager = new SessionManager();
    HistoryManager historyManager = new RemoteHistoryManager(key);
    int thisPlayer = sessionManager.joinGameSession(key);
    return historyManager.getCurrentBoard().toServerChessBoard(key, thisPlayer);
  }

  private static ChessBoard boardFromJSONPath(String jsonFileName) throws IOException {
    JSONObject myJSONObject = createJSONObjectFromPath(jsonFileName);
    int rows = Integer.parseInt(
        myJSONObject.getJSONArray("general").getJSONObject(0).get("columns").toString());
    int columns = Integer.parseInt(
        myJSONObject.getJSONArray("general").getJSONObject(0).get("rows").toString());

    Player[] players = getPlayers(myJSONObject);
    ChessBoard myBoard = new ChessBoard(rows, columns, getTurnCriteria(myJSONObject, players),
        players, getValidStateCheckers(myJSONObject), getEndConditions(myJSONObject));
    setTileActions(myJSONObject, myBoard);
    setStartingPosition(myJSONObject, myBoard);
    return myBoard;
  }


  /**
   * Creates a JSON object from a filepath
   *
   * @param path filepath to JSON file
   * @return JSONObject of the file
   * @throws IOException if file is not found
   */
  private static JSONObject createJSONObjectFromPath(String path) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(path)));
    return new JSONObject(content);
  }

  /**
   * Sets the starting position of the board using tile coordinates
   *
   * @param mainGameFile JSONObject of the file
   * @param myBoard      the board to set the tile actions on
   * @throws IOException throws exception is not valid
   */
  private static void setTileActions(JSONObject mainGameFile, ChessBoard myBoard)
      throws IOException {
    JSONArray customTiles;
    try {
      customTiles = mainGameFile.getJSONArray("tiles");
      LOG.debug("Got custom tiles array");
    } catch (Exception e) {
      LOG.debug("No custom tile entry found");
      return;
    }

    for (int i = 0; i < customTiles.length(); i++) {
      JSONObject rawTileData = customTiles.getJSONObject(i);
      int row = rawTileData.getInt("row");
      int col = rawTileData.getInt("col");

      List<TileAction> tileActions = new ArrayList<>();
      JSONArray tileActionArray = rawTileData.getJSONArray("tileActions");
      for (int j = 0; j < tileActionArray.length(); j++) {
        tileActions.add((TileAction)
            parseArrayOrString(TILE_ACTION_PACKAGE, tileActionArray, j));
      }
      LOG.debug(String.format("Tile actions for (%d, %d): %s", row, col, tileActions));
      try {
        ChessTile tile = myBoard.getTile(Coordinate.of(row, col));
        tile.setSpecialActions(tileActions);
        try {
          tile.setCustomImg(rawTileData.getString("img"));
        } catch (JSONException ignored) {}
      } catch (OutsideOfBoardException e) {
        LOG.debug("Tile action setting failed, out of bounds");
      }
    }
  }

  /**
   * Parses JSON to get the valid state checkers
   *
   * @param mainGameFile JSONObject of the file
   * @return valid state checkers list as defined by the JSON
   * @throws IOException throws exception is not valid
   */
  private static List<ValidStateChecker> getValidStateCheckers(JSONObject mainGameFile)
      throws IOException {
    List<ValidStateChecker> validStateCheckers = new ArrayList<>();
    JSONArray validStateCheckerArray = mainGameFile.getJSONArray("general").getJSONObject(0)
        .getJSONArray("validStateCheckers");
    for (int i = 0; i < validStateCheckerArray.length(); i++) {
      validStateCheckers.add((ValidStateChecker)
          parseArrayOrString(VALID_STATE_CHECKER_PACKAGE, validStateCheckerArray, i));
    }
    LOG.debug(String.format("Valid state checkers: %s", validStateCheckers));
    return validStateCheckers;
  }

  /**
   * Parses the end conditions from the JSON
   *
   * @param mainGameFile JSONObject of the file
   * @return end conditions list as defined by the JSON
   * @throws IOException throws exception is not valid
   */
  private static List<EndCondition> getEndConditions(JSONObject mainGameFile) throws IOException {
    List<EndCondition> endConditions = new ArrayList<>();
    JSONArray endConditionArray = mainGameFile.getJSONArray("general").getJSONObject(0)
        .getJSONArray("endConditions");
    for (int i = 0; i < endConditionArray.length(); i++) {
      endConditions.add((EndCondition)
          parseArrayOrString(END_CONDITION_PACKAGE, endConditionArray, i));
    }
    LOG.debug(String.format("End conditions: %s", endConditions));
    return endConditions;
  }

  /**
   * Parses the turn criteria from the JSON
   *
   * @param mainGameFile JSONObject of the file
   * @return turn criteria as defined by the JSON
   * @throws IOException throws exception is not valid
   */
  private static TurnCriteria getTurnCriteria(JSONObject mainGameFile, Player[] players)
      throws IOException {
    TurnCriteria turnCriteria = (TurnCriteria) createInstance(
        TURN_CRITERIA_PACKAGE + mainGameFile.getJSONArray("general").getJSONObject(0)
            .get("turnCriteria"), new Class[]{Player[].class}, new Object[]{players});
    LOG.debug(String.format("Turn criteria: %s", turnCriteria));
    return turnCriteria;
  }

  /**
   * Creates an instance of the given class name
   *
   * @param className to instantiate
   * @return object of className
   */
  private static Object createInstance(String className, Class<?>[] parameterTypes,
      Object[] parameters) throws IOException {
    try {
      Class<?> clazz = Class.forName(className);
      Constructor<?> constructor = clazz.getConstructor(parameterTypes);
      return constructor.newInstance(parameters);
    } catch (Error | Exception e) {
      LOG.debug(String.format("Class creation failed: %s", className));
      throw new IOException(String.format("Class parsing failed: %s", className));
    }
  }

  /**
   * Parses the JSON to get the players
   *
   * @param myJSONObject JSONObject of the file
   * @return players as defined by the JSON
   */
  private static Player[] getPlayers(JSONObject myJSONObject) {
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
    LOG.debug(String.format("Players: %s", Arrays.toString(players)));
    return players;
  }

  /**
   * Parses the JSON to get the game movement file
   *
   * @param jsonFileName name of the JSON file
   * @return List of movements defined by the coordinates in a given JSON file
   * @throws IOException if error with reading
   */
  private static List<MovementInterface> parseMovementFile(String jsonFileName) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(jsonFileName)));
    JSONObject movementFileObject = new JSONObject(content);
    JSONArray moves = movementFileObject.getJSONArray("moves");
    List<MovementInterface> movements = new ArrayList<>();

    for (int i = 0; i < moves.length(); i++) {
      JSONObject currentMove = moves.getJSONObject(i);
      Coordinate currentCoordinate = parseCoord(
          moves.getJSONObject(i).getJSONArray("relativeCoords"));
      movements.add(new Movement(currentCoordinate, currentMove.getBoolean("infinite")));
    }
    LOG.debug(String.format("Movements in %s: %s", jsonFileName, movements));
    return movements;
  }

  /**
   * Converts a JSONArray of coordinates to a Coordinate
   *
   * @param rawCoords to get coord object from
   * @return coord object based on JSON array
   */
  private static Coordinate parseCoord(JSONArray rawCoords) {
    return new Coordinate(rawCoords.getInt(1), rawCoords.getInt(0));
  }

  /**
   * Gets basic movements from given file
   *
   * @param data file to get data from
   * @param key  to get movement files from
   * @return list of movements based on the file names provided by the list corresponding to JSONKey
   */
  private static List<MovementInterface> getMoveList(JSONObject data, String key)
      throws IOException {
    JSONArray movementFiles = data.getJSONArray(key);
    List<MovementInterface> movements = new ArrayList<>();
    for (int i = 0; i < movementFiles.length(); i++) {
      movements.addAll(
          parseMovementFile(BASIC_MOVEMENT_PACKAGE + movementFiles.getString(i) + JSON_EXTENSION));
    }
    return movements;

  }

  /**
   * Gets movement modifiers from data
   *
   * @param data to get data from
   * @param key to get movement modifiers from
   * @return list of movements modifiers based on the file names provided by the list corresponding
   * to JSONKey
   */
  private static List<MovementModifier> getMovementModifiers(JSONObject data, String key)
      throws IOException {
    List<MovementModifier> movementModifiers = new ArrayList<>();
    JSONArray movementModifierArray = data.getJSONArray(key);
    for (int i = 0; i < movementModifierArray.length(); i++) {
      movementModifiers.add((MovementModifier)
          parseArrayOrString(MOVEMENT_MODIFIER_PACKAGE, movementModifierArray, i));
    }
    return movementModifiers;
  }


  /***
   * Parses a JSON object as either an array or String
   *
   * @param packagePath to get class from
   * @param array to get object from
   * @param index in array
   * @return Object from parsed JSON object
   */
  private static Object parseArrayOrString(String packagePath, JSONArray array, int index)
      throws IOException {
    try {
      JSONArray currentObj = array.getJSONArray(index);
      LOG.debug(String.format("String class: %s", currentObj.getString(0)));
      if(currentObj.length() > 1) LOG.debug(String.format("Parameter: %s", currentObj.getString(1)));
      return currentObj.length() >= 2 ? createInstance(
          packagePath + currentObj.getString(0),
          new Class[]{String.class}, new Object[]{currentObj.getString(1)}) :
          createInstance(packagePath + currentObj.getString(0), new Class[]{},
              new Object[]{});
    } catch (JSONException | IOException e) {
      LOG.debug(String.format("String class: %s", array.getString(index)));
      return createInstance(
          packagePath + array.getString(index), new Class[]{},
          new Object[]{});
    }
  }

  /***
   * @param file to get piece data from
   * @return piece json data based on file
   */
  private static PieceJSONData getPieceJSONData(String file) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(file)));
    JSONObject data = new JSONObject(content);

    return new PieceJSONData(data.getString("pieceName"), data.getString("imgFile"),
        data.getInt("pointValue"), getMoveList(data, "basicMovements"),
        getMoveList(data, "basicCaptures"), getCustomMovements(data, "customMoves"),
        getMovementModifiers(data, "movementModifiers"), getMovementModifiers(data,
        "onInteractionModifier"));
  }

  /***
   * Instantiates movements objects from data based on key
   *
   * @param data to get Strings from
   * @param key to get Strings form
   * @return list of custom movements
   */
  private static List<MovementInterface> getCustomMovements(JSONObject data, String key)
      throws IOException {
    List<MovementInterface> movements = new ArrayList<>();
    JSONArray moveArray = data.getJSONArray(key);
    for (int i = 0; i < moveArray.length(); i++) {
      movements.add((MovementInterface)
          parseArrayOrString(CUSTOM_MOVE_PACKAGE, moveArray, i));
    }
    return movements;
  }

  /**
   * @param mainGameFile JSONObject of the file
   * @param myBoard      board to set pieces on
   * @throws IOException if the file cannot be found
   */
  private static void setStartingPosition(JSONObject mainGameFile, ChessBoard myBoard)
      throws IOException {
    JSONArray pieces = mainGameFile.getJSONArray("pieces");
    List<Piece> pieceList = new ArrayList<>();
    for (int i = 0; i < pieces.length(); i++) {

      JSONObject rawPieceData = pieces.getJSONObject(i);
      String pieceFile = PIECE_JSON_PACKAGE + rawPieceData.getString("pieceFile") + JSON_EXTENSION;
      PieceJSONData pieceJSONData = getPieceJSONData(pieceFile);

      List<MovementInterface> movements = pieceJSONData.basicMovements();
      List<MovementInterface> captures = pieceJSONData.basicCaptures();
      List<MovementInterface> customMovements = getCustomMovements(
          rawPieceData, "customMoves");

      movements.addAll(customMovements);
      movements.addAll(pieceJSONData.customMoves());
      captures.addAll(customMovements);
      captures.addAll(pieceJSONData.customMoves());

      List<MovementModifier> movementModifiers = getMovementModifiers(
          rawPieceData, "movementModifiers");
      movementModifiers.addAll(pieceJSONData.movementModifiers());
      List<MovementModifier> onInteractionModifiers = getMovementModifiers(
          rawPieceData, "onInteractionModifier");
      onInteractionModifiers.addAll(pieceJSONData.onInteractionModifiers());
      LOG.debug(String.format("MMs: %s", movementModifiers));
      PieceData pieceData = new PieceData(Coordinate.of(rawPieceData.getInt("row"), rawPieceData.getInt("col")),
          pieceJSONData.pieceName(), pieceJSONData.pointValue(), rawPieceData.getInt("team"), rawPieceData.getInt("mainPiece") == 1,
          movements, captures, movementModifiers, onInteractionModifiers, pieceJSONData.imgFile());

      Piece currentPiece = new Piece(pieceData);
      pieceList.add(currentPiece);
      LOG.debug("Piece placed");
    }
    myBoard.setPieces(pieceList);
  }
}
