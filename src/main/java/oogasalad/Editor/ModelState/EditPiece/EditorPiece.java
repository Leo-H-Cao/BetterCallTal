package oogasalad.Editor.ModelState.EditPiece;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class EditorPiece {
  private MovementGrid movementGrid;
  private Image image0;
  private Image image1;
  private String pieceID;
  private boolean mainPiece;
  private ArrayList<String> customMoves;
  private int pointValue;
  private String pieceName;

  public EditorPiece(String pieceID){
    this.pieceID = pieceID;
    movementGrid = new MovementGrid();
    mainPiece = false;
  }

  public MovementGrid getMovementGrid() {
    return movementGrid;
  }

  public void setMainPiece(boolean main){
    mainPiece = main;
  }

  public boolean getMainPiece(){
    return mainPiece;
  }

  public String getPieceID() {
    return pieceID;
  }

  public void setTile(int x, int y, PieceGridTile tileStatus){
    movementGrid.setTile(x, y, tileStatus);
  }

  public void setInfiniteTiles(int dirX, int dirY){
    movementGrid.setTileInfinite(dirX, dirY);
  }

  public void removeInfiniteTiles(int dirX, int dirY) {
    movementGrid.removeTileInfinite(dirX, dirY);
  }

  public PieceGridTile getTileStatus(int x, int y){
    return movementGrid.getTileStatus(x, y);
  }

  public void setImage(int team, Image image) {
    if(team == 0){this.image0 = image;}
    else{this.image1 = image;}
  }

  public Image getImage(int team){
    if(team == 0){return image0;}
    else{return image1;}
  }

  public void setCustomMoves(ArrayList<String> customMoves){
    this.customMoves = customMoves;
  }

  public ArrayList<String> getCustomMoves(){
    return customMoves;
  }

  public void setMovementGrid(MovementGrid movementGrid) {
    this.movementGrid = movementGrid;
  }

  public void setPieceID(String pieceID) {
    this.pieceID = pieceID;
  }

  public boolean isMainPiece() {
    return mainPiece;
  }

  public int getPointValue() {
    return pointValue;
  }

  public void setPointValue(int pointValue) {
    this.pointValue = pointValue;
  }

  public String getPieceName() {
    return pieceName;
  }

  public void setPieceName(String pieceName) {
    this.pieceName = pieceName;
  }
}
