package oogasalad.Editor.ModelState.BoardState;


import oogasalad.Editor.API.ModifiesBoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesManager;

public class BoardState implements ModifiesBoardState {
  private EditorBoard myEditorBoard;
  private PiecesManager piecesManager;

  public BoardState(PiecesManager statePiecesManager){
    myEditorBoard = new EditorBoard();
    piecesManager = statePiecesManager;
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

  @Override
  public void setPieceStartingLocation(String pieceID, int x, int y) {
    myEditorBoard.addPieceStartingLocation(piecesManager.getPiece(pieceID), x, y);
    piecesManager.setStartingLocation(pieceID, x, y);
  }

  @Override
  public void removePiece(String pieceID, int x, int y) {
    myEditorBoard.removePieceStartingLocation(x, y);
    piecesManager.setStartingLocation(pieceID, -1, -1);
  }


}
