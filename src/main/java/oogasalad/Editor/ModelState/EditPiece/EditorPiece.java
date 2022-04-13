package oogasalad.Editor.ModelState.EditPiece;

import javafx.scene.image.Image;

public class EditorPiece {
  private MovementGrid movementGrid;
  private Image image0;
  private Image image1;
  private String pieceID;
  private boolean mainPiece;

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

  //TODO
  public void setPieceModifier(){
  }
}
