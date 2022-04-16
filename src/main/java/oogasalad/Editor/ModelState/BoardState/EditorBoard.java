package oogasalad.Editor.ModelState.BoardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import oogasalad.Editor.Exceptions.InvalidPieceIDException;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;

public class EditorBoard {

  private final int DEFAULT_BOARD_SIZE = 8;
  private final String PIECE_ID_ERROR = "Invalid pieceID, piece does not exist in board";
  private List<List<EditorTile>> board;
  private SimpleIntegerProperty myWidth;
  private SimpleIntegerProperty myHeight;

  public EditorBoard(){
    myWidth = new SimpleIntegerProperty(DEFAULT_BOARD_SIZE);
    myHeight = new SimpleIntegerProperty(DEFAULT_BOARD_SIZE);
    board = new ArrayList<>(DEFAULT_BOARD_SIZE);
    initializeBoard(DEFAULT_BOARD_SIZE);
  }

  public void changeBoardSize(int width, int height) {
    myWidth.setValue(width);
    myHeight.setValue(height);

    //adjusting height
    if(height < myHeight.get()){
      for(int i = 0; i < myHeight.get() - height; i++){
        board.remove(board.size()-1);
      }
    }
    else{
      for(int i = myHeight.get(); i < height; i++){
        board.add(new ArrayList<>());
        addDefaultRow(myWidth.get(), myHeight.get());
      }
    }

    //adjusting width
    if(width < myWidth.get()){
      for(int i = 0; i < height; i++){
        for(int j = myWidth.get()-1; j >= width; j--){
          board.get(i).remove(j);
        }
      }
    }
    else{
      for(int i = 0; i< height; i++){
        for(int j = myWidth.get(); j < width; j++){
          board.get(i).add(new EditorTile(i, j));
        }
      }
    }
  }

  public void setTileEffect(int x, int y, String effectString){
    TileEffect effect = TileEffect.valueOf(effectString.toUpperCase(Locale.ROOT));
    board.get(y).get(x).setTileEffect(effect);
  }

  public void addPieceStartingLocation(String pieceID, int x, int y, int team){
    board.get(y).get(x).addPiece(pieceID, team);
  }

  public void removePieceStartingLocation(String pieceID){
    EditorCoordinate pieceLocation = findPieceLocation(pieceID);
    board.get(pieceLocation.getY()).get(pieceLocation.getX()).removePiece();
  }

  public SimpleIntegerProperty getBoardWidth(){
    return myWidth;
  }

  public SimpleIntegerProperty getBoardHeight(){
    return myHeight;
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return findPieceLocation(pieceID);
  }

  public EditorTile getTile(int x, int y){
    return board.get(y).get(x);
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
