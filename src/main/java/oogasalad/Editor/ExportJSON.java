package oogasalad.Editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.LibraryPiece;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;
import org.json.JSONObject;

public class ExportJSON {

  private PiecesState myPiecesState;
  private GameRulesState myGameRulesState;
  private BoardState myBoardState;
  private JSONObject myJSONObject;

  public ExportJSON(PiecesState piecesState, GameRulesState gameRulesState, BoardState boardState){
    myPiecesState = piecesState;
    myGameRulesState = gameRulesState;
    myBoardState = boardState;

    myJSONObject= new JSONObject();
  }

  public void writeToJSON(){
    ObjectMapper objectMapper = new ObjectMapper();
    LibraryPiece piece = myPiecesState.getAllPieces().get(0);
    try{
      objectMapper.writeValue(new File("data/piece.json"), piece);
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }


}
