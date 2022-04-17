package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;

public class PieceExport {
  private final int PIECE_LOCATION = 3;

  private int row;
  private int col;
  private String pieceName;
  private String imgFile;
  private int pointValue;
  private int team;
  private int mainPiece;
  private ArrayList<String> customMoves;
  private ArrayList<String> movementModifiers;
  private ArrayList<String> onInteractionModifier;
  private MovementGrid movementGrid;
  private ArrayList<BasicMovementExport> basicMovements;
  private ArrayList<BasicMovementExport> basicCaptures;

  public PieceExport(int row, int col, EditorPiece editorPiece, int team){
    this.row = row;
    this.col = col;
    this.team = team;
    pieceName = editorPiece.getPieceName();
    imgFile = editorPiece.getImage(team).getValue().getUrl().split("/classes/")[1];
    pointValue = editorPiece.getPointValue();
    mainPiece = editorPiece.isMainPiece() ? 1 : 0;
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    movementModifiers = new ArrayList<>();
    onInteractionModifier = new ArrayList<>();
    movementGrid = editorPiece.getMovementGrid();
    basicMovements = new ArrayList<>();
    basicCaptures = basicMovements;
    createBasicMovements();
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public String getPieceName() {
    return pieceName;
  }

  public String getImgFile() {
    return imgFile;
  }

  public int getPointValue() {
    return pointValue;
  }

  public int getTeam() {
    return team;
  }

  public int getMainPiece() {
    return mainPiece;
  }

  public ArrayList<String> getCustomMoves() {
    return customMoves;
  }

  public ArrayList<String> getMovementModifiers() {
    return movementModifiers;
  }

  public ArrayList<String> getOnInteractionModifier() {
    return onInteractionModifier;
  }

  public ArrayList<BasicMovementExport> getBasicMovements() {
    return basicMovements;
  }

  public ArrayList<BasicMovementExport> getBasicCaptures() {
    return basicCaptures;
  }

  private void createBasicMovements(){
    handleInfiniteMovement();
    for(int y = 0; y < MovementGrid.PIECE_GRID_SIZE; y++){
      for(int x = 0; x < MovementGrid.PIECE_GRID_SIZE; x++){
        if(movementGrid.getTileStatus(x, y) == PieceGridTile.OPEN){
          int relX = x-PIECE_LOCATION;
          int relY = PIECE_LOCATION-y;
          basicMovements.add(new BasicMovementExport(relX, relY, false));
        }
      }
    }
  }
  private void handleInfiniteMovement(){
    for(int y = 0; y < MovementGrid.PIECE_GRID_SIZE; y+=3){
      for(int x = 0; x < MovementGrid.PIECE_GRID_SIZE; x += 3){
        if(movementGrid.getTileStatus(x, y) == PieceGridTile.INFINITY){
          int relX = (x-PIECE_LOCATION)/PIECE_LOCATION;
          int relY = (PIECE_LOCATION-y)/PIECE_LOCATION;
          setLineClosed(relX, relY);
          basicMovements.add(new BasicMovementExport(relX, relY, true));
        }
      }
    }
  }

  private void setLineClosed(int relX, int relY){
    int row = PIECE_LOCATION-relY;
    int col = PIECE_LOCATION+relX;
    while(row >= 0 && row < MovementGrid.PIECE_GRID_SIZE && col >= 0 && col < MovementGrid.PIECE_GRID_SIZE){
      movementGrid.setTile(col, row, PieceGridTile.CLOSED);
      row -= relY;
      col += relX;
    }
  }
}
