package oogasalad.Editor.ModelState.BoardState;

import javafx.scene.image.Image;

/**
 * Tile class representing a single spot on the editor board, can "own" an EditorPiece
 * @author Leo Cao
 */
public class EditorTile {
  private TileEffect myTileEffect;
  private String pieceID;
  private int team;
  private Image img;

  public EditorTile(){
    myTileEffect = TileEffect.NONE;
    pieceID = null;
    team = -1;
  }

  /**
   * Check if current tile has a piece on it
   * @return true if tile has piece
   */
  public boolean hasPiece(){
    return pieceID != null;
  }

  /**
   * Set effect for this tile
   * @param effect
   */
  public void setTileEffect(TileEffect effect){
    myTileEffect = effect;
  }

  /**
   * Get pieceID of the piece that is on this tile
   * @return
   */
  public String getPieceID(){
    return pieceID;
  }

  /**
   * Getter for piece's team if this tile has a piece
   * @return team 0 or 1 of piece that is on this tile
   */
  public int getTeam() {
    return team;
  }

  /**
   * Put  a piece on this tile through pieceID, and specify which team version of the piece
   * @param pieceID
   * @param team
   */
  public void addPiece(String pieceID, int team){
    this.pieceID = pieceID;
    this.team = team;
  }

  /**
   * Remove piece from this tile
   */
  public void removePiece(){
    pieceID = null;
    team = -1;
    img = null;
  }

  /**
   * Getter for current tile effect of this tile
   * @return current tile effect
   */
  public TileEffect getTileEffect() {
    return myTileEffect;
  }


  /**
   * Get current custom image for this tile
   * @return
   */
  public Image getImg() {
    return img;
  }

  /**
   * Set custom image for this tile
   * @param img
   */
  public void setImg(Image img) {
    this.img = img;
  }

}
