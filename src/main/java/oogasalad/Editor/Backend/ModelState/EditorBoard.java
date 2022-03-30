package oogasalad.Editor.Backend.ModelState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditorBoard {

  private final int DEFAULT_BOARD_SIZE = 8;
  private List<ArrayList<EditorTile>> board;

  public EditorBoard(){
    board = new ArrayList<ArrayList<EditorTile>>(DEFAULT_BOARD_SIZE);
    initializeBoard(DEFAULT_BOARD_SIZE);
  }

  public void changeBoardSize(int width, int height){
    int curWidth  = board.get(0).size();
    int curHeight = board.size();

    //adjusting height
    if(height < curHeight){
      for(int i = 0; i < curHeight - height; i++){
        board.remove(0);
      }
    }
    else{
      for(int i = 0; i < height- curHeight; i++){
        board.add(new ArrayList<EditorTile>());
        addDefaultRow(curWidth);
      }
    }

    //adjusting width
    if(width < curWidth){
      for(int i = 0; i < height; i++){
        for(int j = 0; j < curWidth-width; j++){
          board.get(i).remove(0);
        }
      }
    }
    else{
      for(int i = 0; i< height; i++){
        for(int j = 0; j < width-curWidth; j++){
          board.get(i).add(new EditorTile());
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

  public int getBoardWidth(){
    return board.get(0).size();
  }

  public int getBoardHeight(){
    return board.size();
  }

  public void removePieceStartingLocation(int x, int y){
    board.get(y).get(x).removePiece();
  }

  private void initializeBoard(int boardSize){
    for(int i = 0; i < boardSize; i++){
      board.add(new ArrayList<EditorTile>());
      addDefaultRow(boardSize);
    }
  }

  private void addDefaultRow(int rowWidth){
    for(int j = 0; j < rowWidth; j++){
      board.get(board.size()-1).add(new EditorTile());
    }
  }

}
