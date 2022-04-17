package oogasalad.Editor.ModelState.BoardState;

public class EditorTile {
  private TileEffect myTileEffect;
  private String pieceID;
  private int team;

  public EditorTile(int x, int y){
    myTileEffect = TileEffect.NONE;
    pieceID = null;
    team = -1;
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

  public int getTeam() {
    return team;
  }

  public void addPiece(String pieceID, int team){
    this.pieceID = pieceID;
    this.team = team;
  }

  public void removePiece(){
    pieceID = null;
    team = -1;
  }
}
