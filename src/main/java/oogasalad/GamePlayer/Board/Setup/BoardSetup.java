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
import org.json.JSONException;
import org.json.JSONObject;

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

  private ChessBoard myBoard;
  private JSONObject mainGameFile;

  //TODO: create custom exception for handling IOException
  public BoardSetup(String JSONFileName) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(JSONFileName)));
    mainGameFile = new JSONObject(content);
  }

  /**
   * @return ChessBoard object constructed from a JSON
   */
  public ChessBoard createLocalBoard() throws IOException{
    int rows = Integer.parseInt(
        mainGameFile.getJSONArray("general").getJSONObject(0).get("columns").toString());
    int columns = Integer.parseInt(
        mainGameFile.getJSONArray("general").getJSONObject(0).get("rows").toString());

    Player[] players = getPlayers();
    myBoard = new ChessBoard(rows, columns, getTurnCriteria(players), players,
        getValidStateCheckers(), getEndConditions());
    setTileActions();
    setStartingPosition();
    return myBoard;
  }

  public ChessBoard createRemoteBoard(String key, int thisPlayer) throws IOException {
    return createLocalBoard();
  }

  public ChessBoard joinRemoteBoard(String key) throws IOException {
    return createLocalBoard();
  }

  /**
   * Sets tile actions, if applicable
   */
  private void setTileActions() throws IOException {
    JSONArray customTiles;
    try {
      customTiles = mainGameFile.getJSONArray("tiles");
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
        tileActions.add(
            (TileAction) createInstance(TILE_ACTION_PACKAGE + tileActionArray.getString(j),
                new Class[]{}, new Object[]{}));
      }
      LOG.debug(String.format("Tile actions for (%d, %d): ", row, col) + tileActions);
      try {
        ChessTile tile = myBoard.getTile(Coordinate.of(row, col));
        tile.setSpecialActions(tileActions);
        try {
          tile.setCustomImg(rawTileData.getString("img"));
        } catch (JSONException ignored){}
      } catch (OutsideOfBoardException e) {
        LOG.debug("Tile action setting failed, out of bounds");
      }
    }
  }

  /**
   * @return valid state checkers list as defined by the JSON
   */
  private List<ValidStateChecker> getValidStateCheckers() throws IOException {
    List<ValidStateChecker> validStateCheckers = new ArrayList<>();
    JSONArray validStateCheckerArray = mainGameFile.getJSONArray("general").getJSONObject(0)
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
   * @return end conditions list as defined by the JSON
   */
  private List<EndCondition> getEndConditions() throws IOException {
    List<EndCondition> endConditions = new ArrayList<>();
    JSONArray endConditionArray = mainGameFile.getJSONArray("general").getJSONObject(0)
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
   * @return turn criteria as defined by the JSON
   */
  private TurnCriteria getTurnCriteria(Player[] players) throws IOException {
    TurnCriteria turnCriteria = (TurnCriteria) createInstance(
        TURN_CRITERIA_PACKAGE + mainGameFile.getJSONArray("general").getJSONObject(0)
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

  /**
   * @return players as defined by the JSON
   */
  private Player[] getPlayers() {
    JSONArray rawData = mainGameFile.getJSONArray("playerInfo");
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

  /**
   * @param rawCoords to get coord object from
   * @return coord object based on JSON array
   */
  private Coordinate parseCoord(JSONArray rawCoords) {
    return new Coordinate(rawCoords.getInt(1), rawCoords.getInt(0));
  }

  /**
   * Gets basic movements from given file
   *
   * @param data file to get data from
   * @param key to get movement files from
   * @return list of movements based on the file names provided by the list corresponding to JSONKey
   */
  private List<MovementInterface> getMoveList(JSONObject data, String key) throws IOException {
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
  private List<MovementModifier> getMovementModifiers(JSONObject data, String key)
      throws IOException {
    List<MovementModifier> movementModifiers = new ArrayList<>();
    JSONArray movementModifierArray = data.getJSONArray(key);
    for (int i = 0; i < movementModifierArray.length(); i++) {
      try {
        JSONArray currentMovementModifier = movementModifierArray.getJSONArray(i);
        movementModifiers.add((MovementModifier) createInstance(
            MOVEMENT_MODIFIER_PACKAGE + currentMovementModifier.getString(0),
            new Class[]{String.class}, new Object[]{currentMovementModifier.getString(1)}));
      } catch (JSONException | IOException e) {
        movementModifiers.add((MovementModifier) createInstance(
            MOVEMENT_MODIFIER_PACKAGE + movementModifierArray.getString(i), new Class[]{},
            new Object[]{}));
      }
    }
    return movementModifiers;
  }

  /***
   * @param file to get piece data from
   * @return piece json data based on file
   */
  private PieceJSONData getPieceJSONData(String file) throws IOException {
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
  private List<MovementInterface> getCustomMovements(JSONObject data, String key)
      throws IOException {
    List<MovementInterface> movements = new ArrayList<>();
    JSONArray moveArray = data.getJSONArray(key);
    for (int i = 0; i < moveArray.length(); i++) {
      movements.add(
          (MovementInterface) createInstance(CUSTOM_MOVE_PACKAGE + moveArray.getString(i),
              new Class[]{}, new Object[]{}));
    }
    return movements;
  }

  /**
   * Sets up pieces on the board
   */
  private void setStartingPosition() throws IOException {
    JSONArray pieces = mainGameFile.getJSONArray("pieces");
    List<Piece> pieceList = new ArrayList<>();
    for (int i = 0; i < pieces.length(); i++) {

      JSONObject rawPieceData = pieces.getJSONObject(i);
      String pieceFile = PIECE_JSON_PACKAGE + rawPieceData.getString("pieceFile") + JSON_EXTENSION;
      PieceJSONData pieceJSONData = getPieceJSONData(pieceFile);

      int startRow = rawPieceData.getInt("row");
      int startCol = rawPieceData.getInt("col");
      Coordinate startingCoordinate = new Coordinate(startRow, startCol);

      String name = pieceJSONData.pieceName();
      String imageFile = pieceJSONData.imgFile();
      int team = rawPieceData.getInt("team");
      int pointValue = pieceJSONData.pointValue();
      boolean mainPiece = rawPieceData.getInt("mainPiece") == 1;

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
      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, mainPiece,
          movements, captures, movementModifiers, onInteractionModifiers, imageFile);

      Piece currentPiece = new Piece(pieceData);
      pieceList.add(currentPiece);
//      myBoard.placePiece(new Coordinate(startRow, startCol), currentPiece);
      LOG.debug("Piece placed");
    }
    myBoard.setPieces(pieceList);
  }
}
