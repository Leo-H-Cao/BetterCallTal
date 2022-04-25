package oogasalad.Editor.ModelState.BoardState;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import oogasalad.Editor.Exceptions.InvalidPieceIDException;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;

/**
 * Underlying board that reflects changes made by user in editor
 * @author Leo Cao
 */
public class EditorBoard {

  public static final int DEFAULT_BOARD_SIZE = 8;
  private final List<List<EditorTile>> board;
  private final SimpleIntegerProperty myWidth;
  private final SimpleIntegerProperty myHeight;

  public EditorBoard(){
    myWidth = new SimpleIntegerProperty(DEFAULT_BOARD_SIZE);
    myHeight = new SimpleIntegerProperty(DEFAULT_BOARD_SIZE);
    board = new ArrayList<>(DEFAULT_BOARD_SIZE);
    initializeBoard();
  }

  /**
   * Adjusts board width according to size set by user
   * @param newWidth width of board set by user
   */
  public void changeWidth(int newWidth) {
    if(newWidth < myWidth.get()){
      for(int i = 0; i < myHeight.getValue(); i++){
        if (myWidth.get() > newWidth) {
          board.get(i).subList(newWidth, myWidth.get()).clear();
        }
      }
    }
    else{
      for(int i = 0; i< myHeight.getValue(); i++){
        for(int j = myWidth.get(); j < newWidth; j++){
          board.get(i).add(new EditorTile());
        }
      }
    }

    myWidth.setValue(newWidth);
  }

  /**
   * Adjusts board height according to size set by user
   * @param newHeight height of board set by user
   */
  public void changeHeight(int newHeight) {
    if(newHeight < myHeight.get()){
      for(int i = 0; i < myHeight.get() - newHeight; i++){
        board.remove(board.size()-1);
      }
    }
    else{
      for(int i = myHeight.get(); i < newHeight; i++){
        board.add(new ArrayList<>());
        addDefaultRow(myWidth.get(), myHeight.get());
      }
    }
    myHeight.setValue(newHeight);
  }

  /**
   * Removes piece starting position from certain tile
   * @param x coord of tile to be removed
   * @param y coord of tile to be removed
   */
  public void clearTile(int x, int y){
    board.get(y).get(x).removePiece();
  }

  /**
   * Change modifier of certain tile on board
   * @param x coord of tile with new effect
   * @param y coord of tile with new effect
   * @param tileEffect special modifier of tile behavior
   */
  public void setTileEffect(int x, int y, TileEffect tileEffect){
    board.get(y).get(x).setTileEffect(tileEffect);
  }

  /**
   * Sets starting location for certain piece by placing it on baord
   * @param pieceID of piece that is being placed on board
   * @param x coord of spot on board
   * @param y coord of spot on board
   * @param team black or white version of current piece
   */
  public void addPieceStartingLocation(String pieceID, int x, int y, int team, Image pieceImage){
    board.get(y).get(x).addPiece(pieceID, team);
    board.get(y).get(x).setImg(pieceImage);
  }

  public SimpleIntegerProperty getWidth(){
    return myWidth;
  }

  public SimpleIntegerProperty getHeight(){
    return myHeight;
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return findPieceLocation(pieceID);
  }

  public EditorTile getTile(int x, int y){
    return board.get(y).get(x);
  }

  /**
   * Sets tile of certain x and y in board to new image
   * @param x coord of tile being edited
   * @param y coord of tile being edited
   * @param img new image of tile
   */
  public void setTileImage(int x, int y, Image img){
    board.get(y).get(x).setImg(img);
  }

  /**
   * Helper that iterates through board to return location of certain piece identified by pieceID
   * @param pieceID of piece that is selected
   * @return piece with given pieceID
   */
  private EditorCoordinate findPieceLocation(String pieceID) {
    for(int i = 0; i < board.size(); i++){
      for(int j = 0; j < board.get(0).size(); j++){
        if(!board.get(i).get(j).hasPiece()){
          continue;
        }
        if(board.get(i).get(j).getPieceID().equals(pieceID)){
          return new EditorCoordinate(j, i);
        }
      }
    }
    String PIECE_ID_ERROR = "Invalid pieceID, piece does not exist in board";
    throw new InvalidPieceIDException(PIECE_ID_ERROR);
  }

  private void initializeBoard(){
    for(int i = 0; i < EditorBoard.DEFAULT_BOARD_SIZE; i++){
      board.add(new ArrayList<>());
      addDefaultRow(EditorBoard.DEFAULT_BOARD_SIZE, i);
    }
  }

  private void addDefaultRow(int rowWidth, int rowNum){
    for(int j = 0; j < rowWidth; j++){
      board.get(board.size()-1).add(new EditorTile());
    }
  }
}
