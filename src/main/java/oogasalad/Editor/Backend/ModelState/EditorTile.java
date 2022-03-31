package oogasalad.Editor.Backend.ModelState;

public class EditorTile {
  private TileEffect myTileEffect;
  private EditorPiece myEditorPiece;

  public EditorTile(){
    myTileEffect = TileEffect.NONE;
    myEditorPiece = null;
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
