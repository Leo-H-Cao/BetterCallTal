package oogasalad.Editor.Backend.ModelState;

import oogasalad.Editor.Backend.API.ModifiesPiecesState;

public class PiecesState  extends ModelState implements ModifiesPiecesState {

  public PiecesState(){
    super();
  }

  @Override
  public PieceInfo getPieceInfo(String pieceID) {
    return piecesManager.getPiece(pieceID).getPieceInfo();
  }

  @Override
  public void changePieceImage(String pieceID, String imageFile) {
    piecesManager.changePieceImage(pieceID, imageFile);
  }

  @Override
  public EditorPiece createCustomPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName, int startX, int startY) {
    PieceInfo pieceInfo = new PieceInfo(startX, startY, image, teamNumber);
    return piecesManager.createPiece(points,movementRules, pieceID, pieceName, pieceInfo);
  }

  @Override
  public void changePieceMovement(String pieceID, MovementRules movementRules) {
    piecesManager.changePieceMovement(pieceID, movementRules);
  }

  @Override
  public void setPiecePointValue(String pieceID, int points){
    piecesManager.setPiecePointValue(pieceID, points);
  }

  @Override
  public void setPieceName(String pieceID, String name){
    piecesManager.setPieceName(pieceID, name);
  }

  public void setPieceStart(String pieceID, int x, int y){
    piecesManager.setStartingLocation(pieceID, x, y);
  }

}
