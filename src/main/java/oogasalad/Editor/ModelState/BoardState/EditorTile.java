package oogasalad.Editor.ModelState.BoardState;

import oogasalad.Editor.ModelState.PiecesState.LibraryPiece;

public class EditorTile {
  private TileEffect myTileEffect;
  private LibraryPiece myLibraryPiece;
  private int myCoordX;
  private int myCoordY;

  public EditorTile(int x, int y){
    myTileEffect = TileEffect.NONE;
    myLibraryPiece = null;
    myCoordX = x;
    myCoordY = y;
  }

  public boolean hasPiece(){
    return myLibraryPiece != null;
  }

  public void setTileEffect(TileEffect effect){
    myTileEffect = effect;
  }

  public LibraryPiece getPiece(){
    return myLibraryPiece;
  }

  public void addPiece(LibraryPiece piece){
    myLibraryPiece = piece;
  }

  public void removePiece(){
    myLibraryPiece = null;
  }
}
