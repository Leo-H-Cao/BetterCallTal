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

  public void addTileEffect(int x, int y, String effect) {
    myEditorBoard.addTileEffect(x, y, effect);
  }

  public void deleteTileEffect(int x, int y) {
    myEditorBoard.deleteTileEffect(x, y);
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
