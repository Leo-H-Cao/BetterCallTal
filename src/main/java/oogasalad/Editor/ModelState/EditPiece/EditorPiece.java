package oogasalad.Editor.ModelState.EditPiece;

import javafx.scene.image.Image;

public class EditorPiece {
  private MovementGrid movementGrid;
  private Image image0;
  private Image image1;
  private String pieceID;

  public EditorPiece(String pieceID){
    this.pieceID = pieceID;
    movementGrid = new MovementGrid();
  }

  public MovementGrid getMovementGrid() {
    return movementGrid;
  }

  public String getPieceID() {
    return pieceID;
  }

  public void setTileOpen(int x, int y){
    movementGrid.setTileOpen(x, y);
  }

  public void setTileClosed(int x, int y){
    movementGrid.setTileClosed(x, y);
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
