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

  private PiecesState piecesState;
  private GameRulesState gameRulesState;
  private BoardState boardState;
  private String JSONString;
  private GeneralExport generalExport;

  public ExportJSON(PiecesState piecesState, GameRulesState gameRulesState, BoardState boardState){
    this.piecesState = piecesState;
    this.gameRulesState = gameRulesState;
    this.boardState = boardState;
    JSONString = "";
    createGeneralExportObject();
  }

  public void writeToJSON(){
    ObjectMapper objectMapper = new ObjectMapper();
    try{
      JSONString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(generalExport);
      System.out.println(JSONString);
    }
    catch (IOException e){
      //TODO: display exception
      e.printStackTrace();
    }
  }

  private void createGeneralExportObject(){
    generalExport = new GeneralExport(boardState.getBoardHeight(),
        boardState.getBoardWidth());
    generalExport.setTurnCriteria(gameRulesState.getTurnCriteria());
    generalExport.setEndConditions(gameRulesState.getWinConditions());
    generalExport.setColors(gameRulesState.getColors());

  }


}
