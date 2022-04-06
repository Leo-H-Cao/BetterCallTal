package oogasalad.Editor.ModelState.PiecesState;


import java.util.List;
import oogasalad.Editor.API.ModifiesPiecesState;

public class PiecesState implements ModifiesPiecesState {
  private PiecesManager piecesManager;

  public PiecesState(){
    piecesManager = new PiecesManager();
  }

  @Override
  public PieceInfo getPieceInfo(String pieceID) {
    return piecesManager.getPiece(pieceID).getPieceInfo();
  }

  @Override
  public List<PieceInfo> getAllPieceInfo(){
    return piecesManager.getAllPieceInfo();
  }

  public List<EditorPiece> getAllPieces() {
    return piecesManager.getAllPieces();
  }

  @Override
  public void changePieceImage(String pieceID, String imageFile) {
    piecesManager.changePieceImage(pieceID, imageFile);
  }

  @Override
  public EditorPiece createCustomPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName) {
    PieceInfo pieceInfo = new PieceInfo(image, teamNumber);
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

  public PiecesManager getPiecesManager(){
    return piecesManager;
  }
}
