package oogasalad.Editor.ModelState.PiecesState;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;

public class PiecesState {
  private ArrayList<LibraryPiece> availablePieces;

  public PiecesState(){
    availablePieces = new ArrayList<>();
  }

  public LibraryPiece getPiece(String pieceID) {
    return findPiece(pieceID);
  }

  public List<LibraryPiece> getAllPieces(){
    return availablePieces;
  }

  public void changePieceImage(String pieceID, Image imageFile) {
    findPiece(pieceID).setPieceImage(imageFile);
  }

  public LibraryPiece createCustomPiece(int points, int teamNumber, Image image, EditorPiece editorPiece, String pieceName) {
    LibraryPiece newPiece = new LibraryPiece(points, editorPiece, pieceName, teamNumber, image);
    availablePieces.add(newPiece);
    return newPiece;
  }

  public void changePieceMovement(String pieceID, MovementGrid movementGrid) {
    findPiece(pieceID).setPieceMovement(movementGrid);
  }

  public void setPiecePointValue(String pieceID, int points){
    findPiece(pieceID).setPointValue(points);
  }

  public void setPieceName(String pieceID, String name){
    findPiece(pieceID).setPieceName(name);
  }


  private LibraryPiece findPiece(String pieceID){
    for(LibraryPiece piece : availablePieces){
      if(piece.getPieceID().equals(pieceID)){
        return piece;
      }
    }
    return null;
  }
}
