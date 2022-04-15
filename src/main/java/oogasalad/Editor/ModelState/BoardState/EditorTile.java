package oogasalad.Editor.ModelState.BoardState;

import oogasalad.Editor.ModelState.PiecesState.LibraryPiece;

public class EditorTile {
  private TileEffect myTileEffect;
  private String pieceID;
  private int myCoordX;
  private int myCoordY;

  public EditorTile(int x, int y){
    myTileEffect = TileEffect.NONE;
    pieceID = null;
    myCoordX = x;
    myCoordY = y;
  }

  public boolean hasPiece(){
    return pieceID != null;
  }

  public void setTileEffect(TileEffect effect){
    myTileEffect = effect;
  }

  public String getPieceID(){
    return pieceID;
  }

  public void addPiece(String pieceID){
    this.pieceID = pieceID;
  }

  public void removePiece(){
    pieceID = null;
  }
}
