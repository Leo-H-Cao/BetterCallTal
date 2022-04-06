package oogasalad.Editor.ModelState.BoardState;


import oogasalad.Editor.API.ModifiesBoardState;
import oogasalad.Editor.ModelState.PiecesState.EditorPiece;
import oogasalad.Editor.ModelState.ModelState;
import oogasalad.Editor.ModelState.PiecesState.MovementRules;

public class BoardState extends ModelState implements ModifiesBoardState {

  public BoardState(){
    super();
  }

  @Override
  public void changeBoardSize(int width, int height) {
    myEditorBoard.changeBoardSize(width, height);
  }

  @Override
  public void addTileEffect(int x, int y, String effect) {
    myEditorBoard.addTileEffect(x, y, effect);
  }

  @Override
  public void deleteTileEffect(int x, int y) {
    myEditorBoard.deleteTileEffect(x, y);
  }

  @Override
  public int getBoardWidth(){
    return myEditorBoard.getBoardWidth();
  }

  @Override
  public int getBoardHeight(){
    return myEditorBoard.getBoardHeight();
  }

  //  @Override
//  public void setPieceStartingLocation(String pieceID, int x, int y) {
//    myEditorBoard.addPieceStartingLocation(piecesManager.getPiece(pieceID), x, y);
//  }
//
//  @Override
//  public void removePiece(int x, int y) {
//    myEditorBoard.removePieceStartingLocation(x, y);
//  }


}
