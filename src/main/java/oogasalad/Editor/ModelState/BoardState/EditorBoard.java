package oogasalad.Editor.ModelState.BoardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import oogasalad.Editor.ModelState.PiecesState.EditorPiece;

public class EditorBoard {

  private final int DEFAULT_BOARD_SIZE = 8;
  private List<List<EditorTile>> board;

  public EditorBoard(){
    board = new ArrayList<>(DEFAULT_BOARD_SIZE);
    initializeBoard(DEFAULT_BOARD_SIZE);
  }

  public void changeBoardSize(int width, int height){
    int curWidth  = board.get(0).size();
    int curHeight = board.size();

    //adjusting height
    if(height < curHeight){
      for(int i = 0; i < curHeight - height; i++){
        board.remove(board.size()-1);
      }
    }
    else{
      for(int i = curHeight; i < height; i++){
        board.add(new ArrayList<EditorTile>());
        addDefaultRow(curWidth, curHeight);
      }
    }

    //adjusting width
    if(width < curWidth){
      for(int i = 0; i < height; i++){
        for(int j = curWidth-1; j >= width; j--){
          board.get(i).remove(j);
        }
      }
    }
    else{
      for(int i = 0; i< height; i++){
        for(int j = curWidth; j < width; j++){
          board.get(i).add(new EditorTile(i, j));
        }
      }
    }
  }

  public void addTileEffect(int x, int y, String effectString){
    TileEffect effect = TileEffect.valueOf(effectString.toUpperCase(Locale.ROOT));
    board.get(y).get(x).setTileEffect(effect);
  }

  public void deleteTileEffect(int x, int y){
    board.get(y).get(x).deleteTileEffect();
  }

  public void addPieceStartingLocation(EditorPiece piece, int x, int y){
    board.get(y).get(x).addPiece(piece);
  }

  public void removePieceStartingLocation(int x, int y){
    board.get(y).get(x).removePiece();
  }

  public int getBoardWidth(){
    return board.get(0).size();
  }

  public int getBoardHeight(){
    return board.size();
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
