package oogasalad.Editor.ModelState.BoardState;

import javafx.beans.property.SimpleIntegerProperty;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;
import oogasalad.Editor.ModelState.PiecesState.LibraryPiece;
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

  public SimpleIntegerProperty getBoardWidth(){
    return myEditorBoard.getBoardWidth();
  }

  public SimpleIntegerProperty getBoardHeight(){
    return myEditorBoard.getBoardHeight();
  }

  public void setPieceStartingLocation(String pieceID, int x, int y) {
    myEditorBoard.addPieceStartingLocation(pieceID, x, y);
  }

  public void removePiece(String pieceID) {
    myEditorBoard.removePieceStartingLocation(pieceID);
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return myEditorBoard.getPieceLocation(pieceID);
  }

  public EditorTile getTile(int x, int y){
    return myEditorBoard.getTile(x, y);
  }
}
