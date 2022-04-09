package oogasalad.Server;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Movement.MovementModifiers.Atomic;
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

  private static final String BASIC_MOVEMENT_PACKAGE = "/GameEngineResources/BasicMovements/";
  private static final String JSON_EXTENSION = ".json";

  private static final String CUSTOM_MOVE_PACKAGE = "oogasalad.GamePlayer.Movement.CustomMovements.";
  private static final String TURN_CRITERIA_PACKAGE = "oogasalad.GamePlayer.Board.TurnCriteria.";
  private static final String END_CONDITION_PACKAGE = "oogasalad.GamePlayer.Board.EndConditions.";
  private static final String VALID_STATE_CHECKER_PACKAGE = "oogasalad.GamePlayer.ValidStateChecker.";
  private static final String MOVEMENT_MODIFIER_PACKAGE = "oogasalad.GamePlayer.Movement.MovementModifiers.";

  public static void main(String[] args) {
    System.out.println(Atomic.class.getName());
  }

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
   * @param JSONKey to get movement files from
   * @return list of movements based on the file names provided by the list corresponding to JSONKey
   */
  private List<MovementInterface> getMoveList(String JSONKey) {
    return List.of();
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

      List<MovementInterface> movements = getMoveList("basicMovements");
      List<MovementInterface> captures = getMoveList("basicCaptures");
      List<MovementInterface> customMovements = getCustomMovements();
      movements.addAll(customMovements);
      captures.addAll(customMovements);

      List<MovementModifier> movementModifiers = getMovementModifiers("movementModifiers");
      List<MovementModifier> onInteractionModifiers = getMovementModifiers("onInteractionModifier");

      //TODO: REFLECTION DOES THIS
//      movements.addAll(List.of(new Castling(), new DoubleFirstMove()));
//      captures.addAll(List.of(new Castling(), new DoubleFirstMove()));

      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, mainPiece, movements, captures, List.of(), List.of(), imageFile);

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
