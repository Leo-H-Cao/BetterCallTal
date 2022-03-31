package oogasalad.Editor.Backend.ModelState;

import oogasalad.Editor.Backend.API.ModifiesBoardState;
import oogasalad.Editor.Backend.ModelState.ModelState;

public class BoardState extends ModelState implements ModifiesBoardState {

  public BoardState(){
    super();
  }


  @Override
  public void changeBoardSize(int width, int height) {
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
  public void addPieceStartingLocation(String pieceID, int x, int y) {
    myEditorBoard.addPieceStartingLocation(piecesManager.getPiece(pieceID), x, y);
  }

  @Override
  public void removePiece(int x, int y) {
    myEditorBoard.removePieceStartingLocation(x, y);
  }

  @Override
  public int getBoardWidth(){
    return myEditorBoard.getBoardWidth();
  }

  @Override
  public int getBoardHeight(){
    return myEditorBoard.getBoardHeight();
  }


}
