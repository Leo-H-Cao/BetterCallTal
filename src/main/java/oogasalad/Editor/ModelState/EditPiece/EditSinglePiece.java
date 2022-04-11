package oogasalad.Editor.ModelState.EditPiece;

import javafx.scene.image.Image;

public class EditSinglePiece {
  private EditorPiece editorPiece;

  public EditSinglePiece(String pieceID){
    editorPiece = new EditorPiece(pieceID);
  }

  public void setTileOpen(int x, int y){
    editorPiece.getMovementGrid().setTileOpen(x, y);
  }

  public void setTileClosed(int x, int y){
    editorPiece.getMovementGrid().setTileClosed(x, y);
  }

  public void setInfiniteTiles(int dirX, int dirY){
    editorPiece.getMovementGrid().setTileInfinite(dirX, dirY);
  }

  public void removeInfiniteTiles(int dirX, int dirY){
    editorPiece.getMovementGrid().removeTileInfinite(dirX, dirY);
  }

  public Image getImage(int team){
    return editorPiece.getImage(team);
  }

  public void setImage(int team, Image image){
    editorPiece.setImage(team,image);
  }

  public MovementGrid getPieceGrid(){
    return editorPiece.getMovementGrid();
  }

  //TODO
  public void setPieceModifier(){
  }

}
