package oogasalad.Editor.ModelState;


import oogasalad.Editor.API.ModifiesPiecesState;

public class PiecesState extends ModelState implements ModifiesPiecesState {

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

  @Override
  public void changeBoardSize(int width, int height) {

  }

  @Override
  public void addTileEffect(int x, int y, String effect) {

  }

  @Override
  public void deleteTileEffect(int x, int y) {

  }

  @Override
  public void setPieceStartingLocation(String pieceID, int x, int y) {

  }

  @Override
  public void removePiece(int x, int y) {

  }

  @Override
  public int getBoardWidth() {
    return 0;
  }

  @Override
  public int getBoardHeight() {
    return 0;
  }

  public void setPieceStart(String pieceID, int x, int y){
    piecesManager.setStartingLocation(pieceID, x, y);
  }

}
