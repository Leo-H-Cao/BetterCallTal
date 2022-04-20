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
    pieceName = editorPiece.getPieceName().getValue();
    imgFile = editorPiece.getImage(team).getValue().getUrl().split("/classes/")[1];
    pointValue = editorPiece.getPointValue();
    mainPiece = editorPiece.isMainPiece() ? 1 : 0;
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    movementModifiers = new ArrayList<>();
    onInteractionModifier = new ArrayList<>();
    movementGrid = editorPiece.getMovementGrid();
    basicMovements = new ArrayList<>();

    //change for customized captures
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
    for(int y = 0; y < MovementGrid.PIECE_GRID_SIZE; y++){
      for(int x = 0; x < MovementGrid.PIECE_GRID_SIZE; x++){
        PieceGridTile curTile = movementGrid.getTileStatus(x, y);
        if(curTile == PieceGridTile.OPEN || curTile == PieceGridTile.INFINITY){
          int relX = x-PIECE_LOCATION;
          int relY = PIECE_LOCATION-y;
          basicMovements.add(new BasicMovementExport(relX, relY, curTile == PieceGridTile.INFINITY));
        }
      }
    }
  }
}
