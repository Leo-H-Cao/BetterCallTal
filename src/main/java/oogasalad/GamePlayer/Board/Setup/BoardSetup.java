package oogasalad.GamePlayer.Board.Setup;


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
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.TileAction;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardSetup {

  private static final Logger LOG = LogManager.getLogger(BoardSetup.class);
  private static final String BASIC_MOVEMENT_PACKAGE = "doc/GameEngineResources/BasicMovements/";
  private static final String JSON_EXTENSION = ".json";
  private static final String TILE_ACTION_PACKAGE = "oogasalad.GamePlayer.Board.Tiles.CustomTiles.";
  private static final String CUSTOM_MOVE_PACKAGE = "oogasalad.GamePlayer.Movement.CustomMovements.";
  private static final String TURN_CRITERIA_PACKAGE = "oogasalad.GamePlayer.Board.TurnCriteria.";
  private static final String END_CONDITION_PACKAGE = "oogasalad.GamePlayer.Board.EndConditions.";
  private static final String VALID_STATE_CHECKER_PACKAGE = "oogasalad.GamePlayer.ValidStateChecker.";
  private static final String MOVEMENT_MODIFIER_PACKAGE = "oogasalad.GamePlayer.Movement.MovementModifiers.";

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
      throws IOException {
    return createLocalBoard(path);

  }

  /**
   * Creates a ChessBoard from the server's JSON file
   *
   * @param key the key to the game trying to join
   * @return the ChessBoard
   */
  public static ChessBoard joinRemoteBoard(String key) {
    return null;
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
   * @param myJSONObject JSONObject of the file
   * @param myBoard      the board to set the tile actions on
   * @throws IOException throws exception is not valid
   */
  private static void setTileActions(JSONObject myJSONObject, ChessBoard myBoard)
      throws IOException {
    JSONArray customTiles;
    try {
      customTiles = myJSONObject.getJSONArray("tiles");
    } catch (Exception e) {
      LOG.debug("No custom tile entry found");
      return;
    }

    for (int i = 0; i < customTiles.length(); i++) {
      JSONObject rawTileData = customTiles.getJSONObject(i);
      int row = rawTileData.getInt("row");
      int col = rawTileData.getInt("col");
      String img = rawTileData.getString("img");

      List<TileAction> tileActions = new ArrayList<>();
      JSONArray tileActionArray = rawTileData.getJSONArray("tileActions");
      for (int j = 0; j < tileActionArray.length(); j++) {
        tileActions.add(
            (TileAction) createInstance(TILE_ACTION_PACKAGE + tileActionArray.getString(j),
                new Class[]{}, new Object[]{}));
      }
      LOG.debug(String.format("Tile actions for (%d, %d): ", row, col) + tileActions);
      try {
        ChessTile tile = myBoard.getTile(Coordinate.of(row, col));
        tile.setSpecialActions(tileActions);
        tile.setCustomImg(img);
      } catch (OutsideOfBoardException e) {
        LOG.debug("Tile action setting failed, out of bounds");
      }
    }
  }

  /**
   * Parses JSON to get the valid state checkers
   *
   * @param myJSONObject JSONObject of the file
   * @return valid state checkers list as defined by the JSON
   * @throws IOException throws exception is not valid
   */
  private static List<ValidStateChecker> getValidStateCheckers(JSONObject myJSONObject)
      throws IOException {
    List<ValidStateChecker> validStateCheckers = new ArrayList<>();
    JSONArray validStateCheckerArray = myJSONObject.getJSONArray("general").getJSONObject(0)
        .getJSONArray("validStateCheckers");
    for (int i = 0; i < validStateCheckerArray.length(); i++) {
      validStateCheckers.add((ValidStateChecker) createInstance(
          VALID_STATE_CHECKER_PACKAGE + validStateCheckerArray.getString(i), new Class[]{},
          new Object[]{}));
    }
    LOG.debug("Valid state checkers: " + validStateCheckers);
    return validStateCheckers;
  }

  /**
   * Parses the end conditions from the JSON
   *
   * @param myJSONObject JSONObject of the file
   * @return end conditions list as defined by the JSON
   * @throws IOException throws exception is not valid
   */
  private static List<EndCondition> getEndConditions(JSONObject myJSONObject) throws IOException {
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

  /**
   * Parses the turn criteria from the JSON
   *
   * @param myJSONObject JSONObject of the file
   * @return turn criteria as defined by the JSON
   * @throws IOException throws exception is not valid
   */
  private static TurnCriteria getTurnCriteria(JSONObject myJSONObject, Player[] players)
      throws IOException {
    TurnCriteria turnCriteria = (TurnCriteria) createInstance(
        TURN_CRITERIA_PACKAGE + myJSONObject.getJSONArray("general").getJSONObject(0)
            .get("turnCriteria"), new Class[]{Player[].class}, new Object[]{players});
    LOG.debug("Turn criteria: " + turnCriteria);
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
      LOG.debug("Class creation failed: " + className);
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
    LOG.debug("Players: " + Arrays.toString(players));
    return players;
  }

  /**
   * Parses the JSON to get the game movement file
   *
   * @param jsonFileName
   *name of the JSON file
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
    LOG.debug("Movements in " + jsonFileName + ": " + movements);
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
   * Parses the JSON to get the movements salary
   *
   * @param JSONKey      to get movement files from
   * @param myJSONObject JSONObject of the file
   * @return list of movements based on the file names provided by the list corresponding to JSONKey
   */
  private static List<MovementInterface> getMoveList(JSONObject myJSONObject, String JSONKey,
      int pieceIndex) throws IOException {
    JSONArray movementFiles = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex)
        .getJSONArray(JSONKey);
    List<MovementInterface> movements = new ArrayList<>();
    for (int i = 0; i < movementFiles.length(); i++) {
      movements.addAll(
          parseMovementFile(BASIC_MOVEMENT_PACKAGE + movementFiles.getString(i) + JSON_EXTENSION));
    }
    LOG.debug(
        String.format("Movement list for piece %d in %s: %s", pieceIndex, JSONKey, movements));
    return movements;
  }

  /**
   * Parses the JSON to get the pieces
   *
   * @param myJSONObject JSONObject of the file
   * @param pieceIndex   index of the piece
   * @return custom moves as defined by the JSON
   */
  private static List<MovementInterface> getCustomMovements(JSONObject myJSONObject, int pieceIndex)
      throws IOException {
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

  /**
   * @param JSONKey      to get movement files from
   * @param myJSONObject JSONObject of the file
   * @return list of movements modifiers based on the file names provided by the list corresponding
   * to JSONKey
   */
  private static List<MovementModifier> getMovementModifiers(JSONObject myJSONObject,
      String JSONKey, int pieceIndex) throws IOException {
    List<MovementModifier> movementModifiers = new ArrayList<>();
    JSONArray movementModifierArray = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex)
        .getJSONArray(JSONKey);
    for (int i = 0; i < movementModifierArray.length(); i++) {
      movementModifiers.add((MovementModifier) createInstance(
          MOVEMENT_MODIFIER_PACKAGE + movementModifierArray.getString(i), new Class[]{},
          new Object[]{}));
    }
    LOG.debug(
        String.format("Custom movement list for piece %d: %s", pieceIndex, movementModifiers));
    return movementModifiers;
  }

  /**
   * @param myJSONObject JSONObject of the file
   * @param myBoard      board to set pieces on
   * @throws IOException if the file cannot be found
   */
  private static void setStartingPosition(JSONObject myJSONObject, ChessBoard myBoard)
      throws IOException {
    JSONArray pieces = myJSONObject.getJSONArray("pieces");
    List<Piece> pieceList = new ArrayList<>();
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

      List<MovementInterface> movements = getMoveList(myJSONObject, "basicMovements", i);
      List<MovementInterface> captures = getMoveList(myJSONObject, "basicCaptures", i);
      List<MovementInterface> customMovements = getCustomMovements(myJSONObject, i);
      movements.addAll(customMovements);
      captures.addAll(customMovements);

      List<MovementModifier> movementModifiers = getMovementModifiers(myJSONObject,
          "movementModifiers", i);
      List<MovementModifier> onInteractionModifiers = getMovementModifiers(myJSONObject,
          "onInteractionModifier", i);

      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, mainPiece,
          movements, captures, movementModifiers, onInteractionModifiers, imageFile);

      Piece currentPiece = new Piece(pieceData);
      pieceList.add(currentPiece);
//      myBoard.placePiece(new Coordinate(startRow, startCol), currentPiece);
    }
    myBoard.setPieces(pieceList);
  }


}
