package oogasalad.Editor.ModelState.EditPiece;

import javafx.scene.image.Image;

public class EditSinglePiece {
  private EditorPiece editorPiece;

  public EditSinglePiece(String pieceID){
    editorPiece = new EditorPiece(pieceID);
  }

  public void setTileOpen(int x, int y){
    editorPiece.getEditPieceGrid().setTileOpen( x, y);
  }

  public void setTileClosed(int x, int y){
    editorPiece.getEditPieceGrid().setTileClosed(x, y);
  }

  public void setTileInfinite(int dirX, int dirY){
    editorPiece.getEditPieceGrid().setTileInfinite(dirX, dirY);
  }

  public Image getImage(int team){
    return editorPiece.getImage(team);
  }

  public void setImage(int team, Image image){
    editorPiece.setImage(team,image);
  }

  public EditPieceGrid getPieceGrid(){
    return editorPiece.getEditPieceGrid();
  }

  //TODO
  public void setPieceModifier(){

  }

}
