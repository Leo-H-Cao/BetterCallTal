package oogasalad.Server;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
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
  public ChessBoard createBoard(){
    int rows = Integer.parseInt(myJSONObject.getJSONArray("general").getJSONObject(0).get("rows").toString());
    int columns = Integer.parseInt(myJSONObject.getJSONArray("general").getJSONObject(0).get("columns").toString());

    Player[] players = getPlayers();
    myBoard = new ChessBoard(rows, columns, getTurnCriteria(players), players, getValidStateCheckers(), getEndConditions());
    setStartingPosition(myBoard);
    return myBoard;
  }

  /***
   * @return valid state checkers list as defined by the JSON
   */
  private List<ValidStateChecker> getValidStateCheckers() {
    return List.of();
  }

  /***
   * @return end conditions list as defined by the JSON
   */
  private List<EndCondition> getEndConditions() {
    return List.of();
  }

  /***
   * @return turn criteria as defined by the JSON
   */
  private TurnCriteria getTurnCriteria(Player[] players) {
    return new Linear(players);
  }

  /***
   * @return players as defined by the JSON
   */
  private Player[] getPlayers() {
    JSONArray rawData = myJSONObject.getJSONArray("playerInfo");
    Player[] players = new Player[rawData.length()];
    for(int i=0; i<players.length; i++) {
      JSONArray opponents = rawData.getJSONObject(i).getJSONArray("opponents");
      int[] opponentArray = new int[opponents.length()];
      for(int j=0; j<opponents.length(); j++){
        opponentArray[j] = opponents.getInt(j);
      }
      players[i] = new Player(i, opponentArray);
    }
    return players;
  }

  /***
   * @return List of movements defined by the coordinates in a given JSON file
   * @throws IOException if error with reading
   */
  public List<MovementInterface> parseMovementFile(String JSONFileName) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(JSONFileName)));
    JSONObject movementFileObject = new JSONObject(content);
    JSONArray moves = movementFileObject.getJSONArray("moves");
    List<MovementInterface> movements = new ArrayList<>();

    for(int i=0; i<moves.length(); i++) {
      JSONObject currentMove = moves.getJSONObject(i);
      Coordinate currentCoordinate = parseCoord(moves.getJSONObject(i).getJSONArray("relativeCoords"));
      movements.add(new Movement(currentCoordinate, currentMove.getBoolean("infinite")));
    }
    LOG.debug("Movements: " + movements);
    return movements;
  }

  /***
   * @param rawCoords to get coord object from
   * @return coord object based on JSON array
   */
  private Coordinate parseCoord(JSONArray rawCoords) {
    return new Coordinate(rawCoords.getInt(1), rawCoords.getInt(0));
  }


  public static void main(String[] args) {
    try {
      BoardSetup boardSetup = new BoardSetup("doc/GameEngineResources/PresentationBoardUpdated.json");
      boardSetup.parseMovementFile(BASIC_MOVEMENT_PACKAGE + "bishop" + JSON_EXTENSION);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /***
   * @param JSONKey to get movement files from
   * @return list of movements based on the file names provided by the list corresponding to JSONKey
   */
  private List<MovementInterface> getMoveList(String JSONKey, int pieceIndex) {
    JSONArray movementFiles = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex).getJSONArray(JSONKey);
    List<Coordinate> moveList = new ArrayList<>();
    Movement moves;
//    for(int i=0; i<movementFiles.length(); i++){
//      JSONArray currentMovementFile = movementFiles.getJSONArray(i);
//
//      Coordinate relativeCoordinates = new Coordinate(currentMovement.getInt(1), currentMovement.getInt(0));
//      moveList.add(relativeCoordinates);
//    }
//
//    LOG.debug("All movements: " + allMovements);
//
//    if(type.charAt(0) =='u'){
//      moves = new Movement(moveList, true);
//    }
//    else{
//      moves = new Movement(moveList, false);
//    }
//    return moves;
    return null;
  }

  /***
   * @return custom moves as defined by the JSON
   */
  private List<MovementInterface> getCustomMovements() {
    return List.of();
  }

  /***
   * @param JSONKey to get movement files from
   * @return list of movements modifiers based on the file names provided by the list
   * corresponding to JSONKey
   */
  private List<MovementModifier> getMovementModifiers(String JSONKey) {
    return List.of();
  }

  /***
   * Sets up pieces on the board
   *
   * @param board to set pieces on
   */
  private void setStartingPosition(ChessBoard board){
    JSONArray pieces = myJSONObject.getJSONArray("pieces");
    for(int i=0; i<pieces.length(); i++){
      JSONObject rawPieceData = pieces.getJSONObject(i);

//      Movement unboundedMovements = getMovementsByType("unboundedMovements", i);
//      Movement boundedMovements = getMovementsByType("boundedMovements", i);
//      Movement unboundedCaptures = getMovementsByType("unboundedCaptures", i);
//      Movement boundedCaptures = getMovementsByType("boundedCaptures", i);

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
      List<MovementInterface> customMovements = getCustomMovements();
      movements.addAll(customMovements);
      captures.addAll(customMovements);

      List<MovementModifier> movementModifiers = getMovementModifiers("movementModifiers");
      List<MovementModifier> onInteractionModifiers = getMovementModifiers("onInteractionModifier");

      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, mainPiece, movements, captures, movementModifiers, onInteractionModifiers, imageFile);

      Piece currentPiece = new Piece(pieceData);
      myBoard.placePiece(new Coordinate(startRow, startCol), currentPiece);
    }
  }


//  private Movement getMovementsByType(String type, int pieceIndex){
//
//    JSONArray allMovements = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex).getJSONArray(type);
//    List<Coordinate> moveList = new ArrayList<>();
//    Movement moves;
//    for(int i=0; i<allMovements.length(); i++){
//      JSONArray currentMovement = allMovements.getJSONArray(i);
//      Coordinate relativeCoordinates = new Coordinate(currentMovement.getInt(1), currentMovement.getInt(0));
//      moveList.add(relativeCoordinates);
//    }
//
//    LOG.debug("All movements: " + allMovements);
//
//    if(type.charAt(0) =='u'){
//      moves = new Movement(moveList, true);
//    }
//    else{
//      moves = new Movement(moveList, false);
//    }
//    return moves;
//  }
}
