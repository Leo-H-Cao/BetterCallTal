package oogasalad.GamePlayer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
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
    myBoard = new ChessBoard(rows, columns, null ,getPlayers(), null);
    return myBoard;
  }

  private Player[] getPlayers() {
    JSONArray rawData = myJSONObject.getJSONArray("playerInfo");
    Player[] players = new Player[rawData.length()];
    for(int i=0; i<players.length; i++) {

      players[i] = new Player((Integer) rawData.getJSONObject(i).get("team"), (int[]) rawData.getJSONObject(i).get("opponents"));
    }
    return players;
  }

  public static void main(String[] args) throws IOException {
    BoardSetup a = new BoardSetup("data/GameEngineResources/board.json");
    System.out.println(a.myJSONObject.getJSONArray("general").getJSONObject(0).get("rows"));
  }
}
