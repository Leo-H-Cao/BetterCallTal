package oogasalad.Editor.ModelState.BoardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import oogasalad.Editor.Exceptions.InavlidPieceIDException;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;
import oogasalad.Editor.ModelState.PiecesState.LibraryPiece;

public class EditorBoard {

  private final int DEFAULT_BOARD_SIZE = 8;
  private final String PIECE_ID_ERROR = "Invalid pieceID, piece does not exist in board";
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

  public void addPieceStartingLocation(LibraryPiece piece, int x, int y){
    try{
      EditorCoordinate pieceLocation = findPieceLocation(piece.getPieceID());
      board.get(pieceLocation.getY()).get(pieceLocation.getX()).removePiece();
      board.get(y).get(x).addPiece(piece);
    }
    catch(InavlidPieceIDException pieceIDException){
      board.get(y).get(x).addPiece(piece);
    }
  }

  public void removePieceStartingLocation(String pieceID){
    EditorCoordinate pieceLocation = findPieceLocation(pieceID);
    board.get(pieceLocation.getY()).get(pieceLocation.getX()).removePiece();
  }

  public int getBoardWidth(){
    return board.get(0).size();
  }

  public int getBoardHeight(){
    return board.size();
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return findPieceLocation(pieceID);
  }

  private EditorCoordinate findPieceLocation(String pieceID) throws InavlidPieceIDException {
    for(int i = 0; i < board.size(); i++){
      for(int j = 0; j < board.get(0).size(); j++){
        if(!board.get(i).get(j).hasPiece()){
          continue;
        }
        if(board.get(i).get(j).getPiece().getPieceID().equals(pieceID)){
          return new EditorCoordinate(j, i);
        }
      }
    }
    throw new InavlidPieceIDException(PIECE_ID_ERROR);
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
