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
  private EditorBoard myEditorBoard;

  public BoardState(){
    myEditorBoard = new EditorBoard();
  }

  /**
   * Adjusts editor board to size set by user
   * @param width new width of board
   * @param height new height of board
   */
  public void changeBoardSize(int width, int height) {
    myEditorBoard.changeBoardSize(width, height);
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
   * Sets tile of certain x and y in board to new image
   * @param x coord of tile being edited
   * @param y coord of tile being edited
   * @param img new image of tile
   */
  public void setTileImage(int x, int y, Image img){
    myEditorBoard.setTileImage(x,y,img);
  }

  public SimpleIntegerProperty getBoardWidth(){
    return myEditorBoard.getBoardWidth();
  }

  public SimpleIntegerProperty getBoardHeight(){
    return myEditorBoard.getBoardHeight();
  }

  /**
   * Sets starting location for certain piece by placing it on baord
   * @param pieceID of piece that is being placed on board
   * @param x coord of spot on board
   * @param y coord of spot on board
   * @param team black or white version of current piece
   */
  public void setPieceStartingLocation(String pieceID, int x, int y, int team) {
    myEditorBoard.addPieceStartingLocation(pieceID, x, y, team);
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return myEditorBoard.getPieceLocation(pieceID);
  }

  public EditorTile getTile(int x, int y){
    return myEditorBoard.getTile(x, y);
  }

  public void clearTile(int x, int y){
    myEditorBoard.clearTile(x, y);
  }
}
