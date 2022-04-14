package oogasalad.Editor.ModelState.BoardState;

import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class BoardState {
  private EditorBoard myEditorBoard;
  private PiecesState piecesState;

  public BoardState(PiecesState piecesState){
    myEditorBoard = new EditorBoard();
    this.piecesState = piecesState;
  }

  public void changeBoardSize(int width, int height) {
    myEditorBoard.changeBoardSize(width, height);
  }

  public void setTileEffect(int x, int y, String effect) {
    myEditorBoard.setTileEffect(x, y, effect);
  }

  public int getBoardWidth(){
    return myEditorBoard.getBoardWidth();
  }

  public int getBoardHeight(){
    return myEditorBoard.getBoardHeight();
  }

  public void setPieceStartingLocation(String pieceID, int x, int y) {
    myEditorBoard.addPieceStartingLocation(piecesState.getPiece(pieceID), x, y);
  }

  public void removePiece(String pieceID) {
    myEditorBoard.removePieceStartingLocation(pieceID);
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return myEditorBoard.getPieceLocation(pieceID);
  }
}
