package oogasalad.Editor.ExportJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.BoardState.EditorTile;
import oogasalad.Editor.ModelState.BoardState.TileEffect;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportJSON {
  private static final Logger LOG = LogManager.getLogger(ExportJSON.class);
  private final int PIECE_LOCATION = 3;
  private final String OBJECT_MAPPER_ERR_MSG = "JSON object mapper exception";

  private PiecesState piecesState;
  private GameRulesState gameRulesState;
  private BoardState boardState;
  private String JSONString;
  private String JSONTestString;
  private GeneralExport generalExport;
  private ArrayList<PlayerInfoExport> playerInfo;
  private ExportWrapper exportWrapper;
  private ArrayList<PieceExport> pieces;
  private ArrayList<PieceMainExport> piecesMain;
  private ArrayList<TileExport> tiles;
  private HashSet<String> seenPieceID;


  public ExportJSON(PiecesState piecesState, GameRulesState gameRulesState, BoardState boardState){
    seenPieceID = new HashSet<>();
    this.piecesState = piecesState;
    this.gameRulesState = gameRulesState;
    this.boardState = boardState;
    JSONString = "";
    createGeneralExportObject();
    createPlayerInfoObject();
    createPiecesAndTilesExportObjects();
    exportWrapper = new ExportWrapper(generalExport, playerInfo, piecesMain, tiles);
  }

  public void writeToJSON(){
    ObjectMapper objectMapper = new ObjectMapper();
    try{
//      String piecesJSONString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pieces);
//      System.out.println(JSONTestString);

      File movementDir = new File("doc/testing_directory/json_export_test/Pieces");
      if (!movementDir.exists()){
        movementDir.mkdirs();
      }
      for(PieceExport piece : pieces){
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("doc/testing_directory/json_export_test/Pieces/"+piece.getPieceName()+".json"), piece);
      }
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("doc/testing_directory/json_export_test/mainFile.json"), exportWrapper);

    }
    catch (IOException e){
      LOG.warn(OBJECT_MAPPER_ERR_MSG);
    }
  }

  public String getJSONTestString(){
    return JSONTestString;
  }

  private void createGeneralExportObject(){
    generalExport = new GeneralExport(boardState.getBoardHeight().get(),
        boardState.getBoardWidth().get());
    generalExport.setTurnCriteria(gameRulesState.getTurnCriteria());
    generalExport.setEndConditions(gameRulesState.getWinConditions());
    generalExport.setColors(gameRulesState.getColors());
    generalExport.setValidStateChecker(gameRulesState.getValidStateCheckers());
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
    piecesMain = new ArrayList<>();
    for(int y = 0; y < boardState.getBoardHeight().get(); y++){
      for(int x = 0; x < boardState.getBoardWidth().get(); x++){
        EditorTile tile = boardState.getTile(x, y);
        if(tile.hasPiece()){
          EditorPiece curEditorPiece = piecesState.getPiece(tile.getPieceID());
          piecesMain.add(new PieceMainExport(y,x, tile.getTeam(),piecesState.getPiece(tile.getPieceID())));
          if(!seenPieceID.contains(curEditorPiece.getPieceID())){
            pieces.add(new PieceExport(curEditorPiece, tile.getTeam()));
            createBasicMovement(curEditorPiece.getMovementGrid(), curEditorPiece.getPieceName());
            seenPieceID.add(curEditorPiece.getPieceID());
          }
        }
        if(tile.getTileEffect() != TileEffect.NONE || tile.getImg() != null){
          TileExport tileExport = new TileExport(y, x, tile.getImg());
          if(tile.getTileEffect() != TileEffect.NONE){
            tileExport.addTileAction(tile.getTileEffect().toString());
          }
          tiles.add(tileExport);
        }
      }
    }
  }

  private void createBasicMovement(MovementGrid movementGrid, String pieceName){
    BasicMovementExportWrapper movementWrapper = new BasicMovementExportWrapper();
    for(int y = 0; y < MovementGrid.PIECE_GRID_SIZE; y++){
      for(int x = 0; x < MovementGrid.PIECE_GRID_SIZE; x++){
        PieceGridTile curTile = movementGrid.getTileStatus(x, y);
        if(curTile == PieceGridTile.OPEN || curTile == PieceGridTile.INFINITY){
          int relX = x-PIECE_LOCATION;
          int relY = PIECE_LOCATION-y;
          BasicMovementExport basicMovement = new BasicMovementExport(relX, relY, curTile == PieceGridTile.INFINITY);
          movementWrapper.addMovement(basicMovement);
        }
      }
    }
    exportBasicMovement(movementWrapper, pieceName);
  }

  private void exportBasicMovement(BasicMovementExportWrapper basicMovements, String pieceName){
    ObjectMapper objectMapper = new ObjectMapper();
    try{
      File movementDir = new File("doc/testing_directory/json_export_test/BasicMovements");
      if (!movementDir.exists()){
        movementDir.mkdirs();
      }
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("doc/testing_directory/json_export_test/BasicMovements/"+pieceName+"Movement.json"), basicMovements);
    }
    catch (IOException e){
      LOG.warn(OBJECT_MAPPER_ERR_MSG);
    }
  }
}
