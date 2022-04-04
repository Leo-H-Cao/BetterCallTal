package oogasalad.Editor.ModelState;

import oogasalad.Editor.ModelState.TileEffect;

public class EditorTile {
  private TileEffect myTileEffect;
  private EditorPiece myEditorPiece;
  private int myCoordX;
  private int myCoordY;

  public EditorTile(int x, int y){
    myTileEffect = TileEffect.NONE;
    myEditorPiece = null;
    myCoordX = x;
    myCoordY = y;
  }

  public void setTileEffect(TileEffect effect){
    myTileEffect = effect;
  }

  public void deleteTileEffect(){
    myTileEffect = TileEffect.NONE;
  }

  public void addPiece(EditorPiece piece){
    myEditorPiece = piece;
  }

  public void removePiece(){
    myEditorPiece = null;
  }

}
