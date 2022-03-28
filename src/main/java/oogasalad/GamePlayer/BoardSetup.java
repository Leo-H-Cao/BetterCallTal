package oogasalad.GamePlayer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import oogasalad.GamePlayer.Board.ChessBoard;
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
    myBoard = new ChessBoard(rows, columns, null ,2, null);

    return myBoard;
  }


  public static void main(String[] args) throws IOException {
    BoardSetup a = new BoardSetup("data/GameEngineResources/board.json");
    System.out.println(a.myJSONObject.getJSONArray("general").getJSONObject(0).get("rows"));
  }
}
