package oogasalad.Editor.Backend.ModelState;

import oogasalad.Editor.Backend.API.ModifiesBoardState;
import oogasalad.Editor.Backend.API.ModifiesPiecesState;

public class ModelState implements ModifiesBoardState, ModifiesPiecesState {

  private EditorBoard myEditorBoard;
  private PiecesManager piecesManager;

  public ModelState(){
    myEditorBoard = new EditorBoard();
    piecesManager = new PiecesManager();
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

  @Override
  public EditorPiece getPiece(String pieceID) {
    return piecesManager.getPiece(pieceID);
  }

  @Override
  public void changePieceImage(String pieceID, String imageFile) {
    piecesManager.changePieceImage(pieceID, imageFile);
  }

  @Override
  public EditorPiece createCustomPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName) {
    return piecesManager.createPiece(points, teamNumber, image, movementRules, pieceID, pieceName);
  }

  @Override
  public void changePieceMovement(String pieceID, MovementRules movementRules) {
    piecesManager.changePieceMovement(pieceID, movementRules);
  }

  @Override
  public void setPiecePointValue(String pieceID, int points){
    piecesManager.setPiecePointValue(pieceID, points);
  }

  public void setPieceName(String pieceID, String name){
    piecesManager.setPieceName(pieceID, name);
  }
}
