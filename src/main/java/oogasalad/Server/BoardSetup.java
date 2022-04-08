package oogasalad.Server;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.NoEndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.CustomMovements.Castling;
import oogasalad.GamePlayer.Movement.CustomMovements.DoubleFirstMove;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardSetup {

  private static final Logger LOG = LogManager.getLogger(BoardSetup.class);

  private ChessBoard myBoard;
  private JSONObject myJSONObject;

  //TODO: create custom exception for handling IOException
  public BoardSetup(String JSONFileName) throws IOException {
    String content = new String(Files.readAllBytes(Path.of(JSONFileName)));
    myJSONObject = new JSONObject(content);
  }

  public ChessBoard createBoard(){
    int rows = Integer.parseInt(myJSONObject.getJSONArray("general").getJSONObject(0).get("rows").toString());
    int columns = Integer.parseInt(myJSONObject.getJSONArray("general").getJSONObject(0).get("columns").toString());

    myBoard = new ChessBoard(rows, columns, new Linear(getPlayers()) ,getPlayers(), List.of(new NoEndCondition()));
    setStartingPosition(myBoard);
    return myBoard;
  }

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

  private Movement getMovementsByType(String type, int pieceIndex){

    JSONArray allMovements = myJSONObject.getJSONArray("pieces").getJSONObject(pieceIndex).getJSONArray(type);
    List<Coordinate> moveList = new ArrayList<>();
    Movement moves;
    for(int i=0; i<allMovements.length(); i++){
      JSONArray currentMovement = allMovements.getJSONArray(i);
      Coordinate relativeCoordinates = new Coordinate(currentMovement.getInt(1), currentMovement.getInt(0));
      moveList.add(relativeCoordinates);
    }

    LOG.debug("All movements: " + allMovements);

    if(type.charAt(0) =='u'){
      moves = new Movement(moveList, true);
    }
    else{
      moves = new Movement(moveList, false);
    }
    return moves;
  }

  private void setStartingPosition(ChessBoard board){
    JSONArray pieces = myJSONObject.getJSONArray("pieces");
    for(int i=0; i<pieces.length(); i++){
      JSONObject rawPieceData = pieces.getJSONObject(i);

      Movement unboundedMovements = getMovementsByType("unboundedMovements", i);
      Movement boundedMovements = getMovementsByType("boundedMovements", i);
      Movement unboundedCaptures = getMovementsByType("unboundedCaptures", i);
      Movement boundedCaptures = getMovementsByType("boundedCaptures", i);

      int startRow = rawPieceData.getInt("row");
      int startCol = rawPieceData.getInt("col");
      String name = rawPieceData.getString("pieceName");
      String imageFile = rawPieceData.getString("imgFile");
      Coordinate startingCoordinate = new Coordinate(startRow, startCol);
      int team = rawPieceData.getInt("team");
      int pointValue = rawPieceData.getInt("pointValue");
      boolean mainPiece = rawPieceData.getInt("mainPiece") == 1;
      List<MovementInterface> movements = new ArrayList<>();
      List<MovementInterface> captures = new ArrayList<>();
      movements.add(unboundedMovements);
      movements.add(boundedMovements);
      captures.add(unboundedCaptures);
      captures.add(boundedCaptures);

      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, mainPiece, movements, captures, List.of(new Castling(), new DoubleFirstMove()), List.of(), List.of(), imageFile);

      Piece currentPiece = new Piece(pieceData);
      myBoard.placePiece(new Coordinate(startRow, startCol), currentPiece);
    }
  }
}
