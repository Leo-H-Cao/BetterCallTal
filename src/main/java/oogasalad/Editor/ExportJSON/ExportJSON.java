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

/**
 * Handles exporting game configurations set by user,
 * creates objects for serialization according to format
 * that can be parsed by game engine
 * @author Leo Cao
 */
public class ExportJSON {
  private static final Logger LOG = LogManager.getLogger(ExportJSON.class);
  private final int PIECE_LOCATION = 3;
  private final String OBJECT_MAPPER_ERR_MSG = "JSON object mapper exception";

  private PiecesState piecesState;
  private GameRulesState gameRulesState;
  private BoardState boardState;
  private String MainJSONString;
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
    MainJSONString = "";
    createGeneralExportObject();
    createPlayerInfoObject();
    createPiecesAndTilesExportObjects();
    exportWrapper = new ExportWrapper(generalExport, playerInfo, piecesMain, tiles);
  }

  /**
   * Writes pieces to individual pieces JSON, and creates main game file
   * @param parentDir to save the main game configurations file
   */
  public void writeToJSON(File parentDir){
    ObjectMapper objectMapper = new ObjectMapper();
    try{

//      File parentDir = chooser.showDialog(new Stage());
//      if(parentDir != null) {
//        if (!parentDir.exists()){
//          boolean result = parentDir.mkdirs();
//          if (!result) return;
//        }
        for(PieceExport piece : pieces){
          objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("doc/GameEngineResources/Pieces/"+piece.getPieceName()+".json"), piece);
        }
        MainJSONString = objectMapper.writeValueAsString(exportWrapper);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(parentDir.getAbsolutePath()+"/mainFile.json"), exportWrapper);

//      }
      } catch (IOException e) {
      LOG.warn(OBJECT_MAPPER_ERR_MSG);
      throw new RuntimeException(e);
    }
  }

  /**
   * creates general configurations object including turn criteria, end conditions, etc
   * ready to be serialized into JSON
   */
  private void createGeneralExportObject(){
    generalExport = new GeneralExport(boardState.getHeight().get(),
        boardState.getWidth().get());
    generalExport.setTurnCriteria(gameRulesState.getTurnCriteria());
    generalExport.setEndConditions(gameRulesState.getWinConditions());
    generalExport.setColors(gameRulesState.getColors());
    generalExport.setValidStateChecker(gameRulesState.getValidStateCheckers());
  }

  /**
   * creates player info (mappings of player and their opponents) export objects
   */
  private void createPlayerInfoObject(){
    playerInfo = new ArrayList<>();
    HashMap<Integer, ArrayList<Integer>> gameRulesPlayers = gameRulesState.getTeamOpponents();
    for(Integer team : gameRulesPlayers.keySet()){
      playerInfo.add(new PlayerInfoExport(team, gameRulesPlayers.get(team)));
    }
  }

  /**
   * iterates through game board to create export objects for
   * pieces (and their starting positions), and tiles
   */
  private void createPiecesAndTilesExportObjects(){
    pieces = new ArrayList<>();
    tiles = new ArrayList<>();
    piecesMain = new ArrayList<>();
    for(int y = 0; y < boardState.getHeight().get(); y++){
      for(int x = 0; x < boardState.getWidth().get(); x++){
        EditorTile tile = boardState.getTile(x, y);
        if(tile.hasPiece()){
          EditorPiece curEditorPiece = piecesState.getPiece(tile.getPieceID());
          piecesMain.add(new PieceMainExport(y,x, tile.getTeam(),piecesState.getPiece(tile.getPieceID())));
          if(!seenPieceID.contains(curEditorPiece.getPieceID())){
            pieces.add(new PieceExport(curEditorPiece, tile.getTeam()));
            createBasicMovement(curEditorPiece.getMovementGrid(0), curEditorPiece.getPieceName().getValue(), 0);
            createBasicMovement(curEditorPiece.getMovementGrid(1), curEditorPiece.getPieceName().getValue(), 1);
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

  /**
   * creates list of basic movement export objects for each piece
   * @param movementGrid current piece's movement grid
   * @param pieceName used for naming the export file
   * @param teamNum determines which team (black or white) current movement grid is for
   */
  private void createBasicMovement(MovementGrid movementGrid, String pieceName, int teamNum){
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
    exportBasicMovement(movementWrapper, pieceName, teamNum);
  }

  /**
   * Writes basic movements file to doc/GameEngineResources
   * @param basicMovements list of basic movements for current piece
   * @param pieceName used for naming the export file
   * @param teamNum determines which team (black or white) current movement grid is for
   */
  private void exportBasicMovement(BasicMovementExportWrapper basicMovements, String pieceName, int teamNum){
    String team = teamNum == 0 ? "w" : "b";
    ObjectMapper objectMapper = new ObjectMapper();
    try{
      File movementDir = new File("doc/GameEngineResources/BasicMovements");
      if (!movementDir.exists()){
        movementDir.mkdirs();
      }
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(movementDir.getAbsolutePath()+"/"+team+pieceName+"Movement.json"), basicMovements);
    }
    catch (IOException e){
      LOG.warn(OBJECT_MAPPER_ERR_MSG);
    }
  }
}
