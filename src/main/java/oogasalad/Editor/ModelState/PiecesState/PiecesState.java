package oogasalad.Editor.ModelState.PiecesState;


import java.util.List;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;

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

  public LibraryPiece createCustomPiece(int points, int teamNumber, Image image, MovementGrid movementGrid, String pieceID, String pieceName) {
    return piecesManager.createPiece(points, movementGrid, pieceID, pieceName, teamNumber, image);
  }

  public void changePieceMovement(String pieceID, MovementGrid movementGrid) {
    piecesManager.changePieceMovement(pieceID, movementGrid);
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
