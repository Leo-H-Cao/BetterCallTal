package oogasalad.Editor.ExportJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.BoardState.EditorTile;
import oogasalad.Editor.ModelState.BoardState.TileEffect;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportJSON {
  private static final Logger LOG = LogManager.getLogger(ExportJSON.class);

  private PiecesState piecesState;
  private GameRulesState gameRulesState;
  private BoardState boardState;
  private String JSONString;
  private String JSONTestString;
  private GeneralExport generalExport;
  private ArrayList<PlayerInfoExport> playerInfo;
  private ExportWrapper exportWrapper;
  private ArrayList<PieceExport> pieces;
  private ArrayList<TileExport> tiles;


  public ExportJSON(PiecesState piecesState, GameRulesState gameRulesState, BoardState boardState){
    this.piecesState = piecesState;
    this.gameRulesState = gameRulesState;
    this.boardState = boardState;
    JSONString = "";
    createGeneralExportObject();
    createPlayerInfoObject();
    createPiecesAndTilesExportObjects();
    exportWrapper = new ExportWrapper(generalExport, playerInfo, pieces, tiles);
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
//      System.out.println(JSONTestString);

    }
    catch (IOException e){
      LOG.warn("JSON object mapper exception");
    }
  }

  private void createGeneralExportObject(){
    generalExport = new GeneralExport(boardState.getBoardHeight().get(),
        boardState.getBoardWidth().get());
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

  private void createPiecesAndTilesExportObjects(){
    pieces = new ArrayList<>();
    tiles = new ArrayList<>();
    for(int y = 0; y < boardState.getBoardHeight().get(); y++){
      for(int x = 0; x < boardState.getBoardWidth().get(); x++){
        EditorTile tile = boardState.getTile(x, y);
        if(tile.hasPiece()){
          pieces.add(new PieceExport(x, y, piecesState.getPiece(tile.getPieceID()), tile.getTeam()));
        }
        if(tile.getTileEffect() != TileEffect.NONE){
          TileExport tileExport = new TileExport(x, y, tile.getImg());
          tileExport.addTileAction(tile.getTileEffect().toString());
          tiles.add(tileExport);
        }
      }
    }
  }
}
