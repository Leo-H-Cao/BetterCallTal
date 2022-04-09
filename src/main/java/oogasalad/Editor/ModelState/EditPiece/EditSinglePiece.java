package oogasalad.Editor.ModelState.EditPiece;

public class EditSinglePiece {
  private EditPieceGrid editPieceGrid;

  public EditSinglePiece(){
    editPieceGrid = new EditPieceGrid();
  }

  public void setTileOpen(int x, int y){
    editPieceGrid.setTileOpen( x, y);
  }

  public void setTileClosed(int x, int y){
    editPieceGrid.setTileClosed(x, y);
  }

  public void setTileInfinite(int dirX, int dirY){
    editPieceGrid.setTileInfinite(dirX, dirY);
  }

  //TODO
  public void setPieceModifier(){

  }

}
