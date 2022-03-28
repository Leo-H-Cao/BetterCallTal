package oogasalad.Editor.Backend;

import oogasalad.Editor.Backend.API.ModifiesBoardState;

public class ModelState implements ModifiesBoardState {

  private EditorBoard myEditorBoard;

  public ModelState(){
    myEditorBoard = new EditorBoard();
  }


  @Override
  public void changeBoardShape(int width, int height) {
    myEditorBoard.changeBoardSize(width, height);
  }

  @Override
  public void addTileEffect(int x, int y, String effectString) {
    myEditorBoard.addTileEffect(x, y, effectString);
  }

  @Override
  public void deleteTileEffect(int x, int y) {
    myEditorBoard.deleteTileEffect(x, y);
  }

  @Override
  public void addPieceStartingLocation(EditorPiece piece, int x, int y) {
    myEditorBoard.addPieceStartingLocation(piece, x, y);
  }

  @Override
  public void removePiece(int x, int y) {
    myEditorBoard.removePieceStartingLocation(x, y);
  }
}
