package oogasalad.Editor.ExportJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.BoardState.EditorTile;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;

public class ExportJSON {

  private PiecesState piecesState;
  private GameRulesState gameRulesState;
  private BoardState boardState;
  private String JSONString;
  private String JSONTestString;
  private GeneralExport generalExport;
  private ArrayList<PlayerInfoExport> playerInfo;
  private ExportWrapper exportWrapper;
  private ArrayList<PieceExport> pieces;


  public ExportJSON(PiecesState piecesState, GameRulesState gameRulesState, BoardState boardState){
    this.piecesState = piecesState;
    this.gameRulesState = gameRulesState;
    this.boardState = boardState;
    JSONString = "";
    createGeneralExportObject();
    createPlayerInfoObject();
    createPiecesExportObjects();
    exportWrapper = new ExportWrapper(generalExport, playerInfo, pieces);
  }

  public String getJSONTestString(){
    return JSONTestString;
  }

  public void writeToJSON(){
    ObjectMapper objectMapper = new ObjectMapper();
    try{
      JSONString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exportWrapper);
      JSONTestString = objectMapper.writeValueAsString(exportWrapper);
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

  private void createPlayerInfoObject(){
    playerInfo = new ArrayList<>();
    HashMap<Integer, ArrayList<Integer>> gameRulesPlayers = gameRulesState.getTeamOpponents();
    for(Integer team : gameRulesPlayers.keySet()){
      playerInfo.add(new PlayerInfoExport(team, gameRulesPlayers.get(team)));
    }
  }

  private void createPiecesExportObjects(){
    pieces = new ArrayList<>();
    for(int y = 0; y < boardState.getBoardHeight(); y++){
      for(int x = 0; x < boardState.getBoardWidth(); x++){
        EditorTile tile = boardState.getTile(x, y);
        if(tile.hasPiece()){
          pieces.add(new PieceExport(x, y, piecesState.getPiece(tile.getPieceID()), tile.getTeam()));
        }
      }
    }
  }
}
