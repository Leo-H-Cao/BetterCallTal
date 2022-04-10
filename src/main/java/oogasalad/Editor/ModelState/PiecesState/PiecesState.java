package oogasalad.Editor.ModelState.PiecesState;


import java.util.List;
import javafx.scene.image.Image;

public class PiecesState {
  private PiecesManager piecesManager;

  public PiecesState(){
    piecesManager = new PiecesManager();
  }

  public LibraryPiece getPiece(String pieceID) {
    return piecesManager.getPiece(pieceID);
  }

  public List<LibraryPiece> getAllPieces(){
    return piecesManager.getAllPieces();
  }

  public void changePieceImage(String pieceID, Image imageFile) {
    piecesManager.changePieceImage(pieceID, imageFile);
  }

  public LibraryPiece createCustomPiece(int points, int teamNumber, Image image, MovementRules movementRules, String pieceID, String pieceName) {
    return piecesManager.createPiece(points,movementRules, pieceID, pieceName, teamNumber, image);
  }

  public void changePieceMovement(String pieceID, MovementRules movementRules) {
    piecesManager.changePieceMovement(pieceID, movementRules);
  }

  public void setPiecePointValue(String pieceID, int points){
    piecesManager.setPiecePointValue(pieceID, points);
  }

  public void setPieceName(String pieceID, String name){
    piecesManager.setPieceName(pieceID, name);
  }

  public PiecesManager getPiecesManager(){
    return piecesManager;
  }
}
