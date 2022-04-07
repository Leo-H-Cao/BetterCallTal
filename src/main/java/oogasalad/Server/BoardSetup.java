package oogasalad.Server;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardSetup {
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

    myBoard = new ChessBoard(rows, columns, new Linear(getPlayers()) ,getPlayers(), null);
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

  private Movement getMovementsByType(String type){

    JSONArray allMovements = myJSONObject.getJSONArray("pieces").getJSONObject(0).getJSONArray(type);
    List<Coordinate> moveList = new ArrayList<Coordinate>();
    Movement moves;
    for(int i=0; i<allMovements.length(); i++){
      JSONArray currentMovement = allMovements.getJSONArray(i);
      Coordinate relativeCoordinates = new Coordinate(currentMovement.getInt(0), currentMovement.getInt(1));
      moveList.add(relativeCoordinates);
    }

    System.out.println(allMovements);

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

      Movement unboundedMovements = getMovementsByType("unboundedMovements");
      Movement boundedMovements = getMovementsByType("boundedMovements");
      Movement unboundedCaptures = getMovementsByType("unboundedCaptures");
      Movement boundedCaptures = getMovementsByType("boundedCaptures");

      int startRow = rawPieceData.getInt("coordinateX");
      int startCol = rawPieceData.getInt("coordinateY");
      String name = rawPieceData.getString("pieceName");
      String imageFile = rawPieceData.getString("imgFile");
      Coordinate startingCoordinate = new Coordinate(startRow, startCol);
      int team = rawPieceData.getInt("team");
      int pointValue = rawPieceData.getInt("pointValue");
      List<MovementInterface> movements = new ArrayList<>();
      List<MovementInterface> captures = new ArrayList<>();
      movements.add(unboundedMovements);
      movements.add(boundedMovements);
      captures.add(unboundedCaptures);
      captures.add(boundedCaptures);

      PieceData pieceData = new PieceData(startingCoordinate, name, pointValue, team, false, movements, captures, List.of(), List.of(), List.of(), imageFile);
      Piece currentPiece = new Piece(pieceData, board);

      myBoard.placePiece(new Coordinate(startRow, startCol), currentPiece);
    }
  }
}
