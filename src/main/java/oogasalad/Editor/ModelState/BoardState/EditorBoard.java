package oogasalad.Editor.ModelState.BoardState;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import oogasalad.Editor.Exceptions.InvalidPieceIDException;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;

public class EditorBoard {

  private final int DEFAULT_BOARD_SIZE = 8;
  private final String PIECE_ID_ERROR = "Invalid pieceID, piece does not exist in board";
  private List<List<EditorTile>> board;
  private final SimpleIntegerProperty myWidth;
  private final SimpleIntegerProperty myHeight;

  public EditorBoard(){
    myWidth = new SimpleIntegerProperty(DEFAULT_BOARD_SIZE);
    myHeight = new SimpleIntegerProperty(DEFAULT_BOARD_SIZE);
    board = new ArrayList<>(DEFAULT_BOARD_SIZE);
    initializeBoard(DEFAULT_BOARD_SIZE);
  }

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
          board.get(i).add(new EditorTile(i, j));
        }
      }
    }

    myWidth.setValue(newWidth);
  }

  public void changeHeight(int x) {
    if(x < myHeight.get()){
      for(int i = 0; i < myHeight.get() - x; i++){
        board.remove(board.size()-1);
      }
    }
    else{
      for(int i = myHeight.get(); i < x; i++){
        board.add(new ArrayList<>());
        addDefaultRow(myWidth.get(), myHeight.get());
      }
    }
    myHeight.setValue(x);
  }

  // !!! Deprecated
//  public void changeBoardSize(int width, int height) {
//    myWidth.setValue(width);
//    myHeight.setValue(height);
//
//    //adjusting height
//    if(height < myHeight.get()){
//      for(int i = 0; i < myHeight.get() - height; i++){
//        board.remove(board.size()-1);
//      }
//    }
//    else{
//      for(int i = myHeight.get(); i < height; i++){
//        board.add(new ArrayList<>());
//        addDefaultRow(myWidth.get(), myHeight.get());
//      }
//    }
//
//    //adjusting width
//    if(width < myWidth.get()){
//      for(int i = 0; i < height; i++){
//        for(int j = myWidth.get()-1; j >= width; j--){
//          board.get(i).remove(j);
//        }
//      }
//    }
//    else{
//      for(int i = 0; i< height; i++){
//        for(int j = myWidth.get(); j < width; j++){
//          board.get(i).add(new EditorTile(i, j));
//        }
//      }
//    }
//  }

  public void clearTile(int x, int y){
    board.get(y).get(x).removePiece();
  }

  public void setTileEffect(int x, int y, TileEffect tileEffect){
    board.get(y).get(x).setTileEffect(tileEffect);
  }

  public void addPieceStartingLocation(String pieceID, int x, int y, int team){
    board.get(y).get(x).addPiece(pieceID, team);
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

  public void setTileImage(int x, int y, Image img){
    board.get(y).get(x).setImg(img);
  }

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
    throw new InvalidPieceIDException(PIECE_ID_ERROR);
  }

  private void initializeBoard(int boardSize){
    for(int i = 0; i < boardSize; i++){
      board.add(new ArrayList<>());
      addDefaultRow(boardSize, i);
    }
  }

  private void addDefaultRow(int rowWidth, int rowNum){
    for(int j = 0; j < rowWidth; j++){
      board.get(board.size()-1).add(new EditorTile(rowNum, j));
    }
  }
}
