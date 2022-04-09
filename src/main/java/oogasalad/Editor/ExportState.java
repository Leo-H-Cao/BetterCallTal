package oogasalad.Editor;

import java.io.FileWriter;
import java.io.IOException;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;
import org.json.JSONObject;

public class ExportState {

  private PiecesState myPiecesState;
  private GameRulesState myGameRulesState;
  private BoardState myBoardState;
  private JSONObject myJSONObject;

  public ExportState(PiecesState piecesState, GameRulesState gameRulesState, BoardState boardState){
    myPiecesState = piecesState;
    myGameRulesState = gameRulesState;
    myBoardState = boardState;

    myJSONObject= new JSONObject();
  }

  public void writeToJSON(){
    myJSONObject.put("key", "value");
    try {
      FileWriter file = new FileWriter("E:/output.json");
      file.write(myJSONObject.toString());
      file.close();
    } catch (IOException e) {
      // TODO catch block
      e.printStackTrace();
    }
  }


}
