package oogasalad.Editor.ModelState.BoardState;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

/**
 * Class that tracks changes made to the editor board, including tiles effects, piece starting positions
 * @author Leo Cao
 */

public class BoardState {
  private final EditorBoard myEditorBoard;
  private final PiecesState piecesState;

  public BoardState(PiecesState piecesState){
    myEditorBoard = new EditorBoard();
    this.piecesState = piecesState;
  }


  /**
   * Adjusts editor board to width size set by user
   * @param newValue new width of board
   */
  public void setWidth(int newValue) {
    myEditorBoard.changeWidth(newValue);
  }

  /**
   * Adjusts editor board to height size set by user
   * @param newValue new height of board
   */
  public void setHeight(int newValue) {
    myEditorBoard.changeHeight(newValue);
  }


  /**
   * Change modifier of certain tile on board
   * @param x coord of tile with new effect
   * @param y coord of tile with new effect
   * @param effect special modifier of tile behavior
   */

  public void setTileEffect(int x, int y, TileEffect effect) {
    myEditorBoard.setTileEffect(x, y, effect);
  }

  /**
   * Get current width of board
   * @return current width
   */
  public SimpleIntegerProperty getWidth(){
    return myEditorBoard.getWidth();
  }

  /**
   * Get current height of board
   * @return current height
   */
  public SimpleIntegerProperty getHeight(){
    return myEditorBoard.getHeight();
  }

  /**
   * Sets starting location for certain piece by placing it on baord
   * @param pieceID of piece that is being placed on board
   * @param x coord of spot on board
   * @param y coord of spot on board
   * @param team black or white version of current piece
   */
  public void setPieceStartingLocation(String pieceID, int x, int y, int team) {
    Image pieceImg = piecesState.getPiece(pieceID).getImage(team).getValue();
    myEditorBoard.addPieceStartingLocation(pieceID, x, y, team, pieceImg);
  }

  /**
   * Returns location of piece given certain pieceID
   * @param pieceID, unique identifier of piece that is being found
   * @return location of piece
   */
  public EditorCoordinate getPieceLocation(String pieceID){
    return myEditorBoard.getPieceLocation(pieceID);
  }

  /**
   * Get tile at certain location of board
   * @param x coord of tile
   * @param y coord of tile
   * @return tile at location (x,y)
   */
  public EditorTile getTile(int x, int y){
    return myEditorBoard.getTile(x, y);
  }

  /**
   * Removes piece starting position from certain tile
   * @param x coord of tile to be removed
   * @param y coord of tile to be removed
   */
  public void clearTile(int x, int y){
    myEditorBoard.clearTile(x, y);
  }
}
