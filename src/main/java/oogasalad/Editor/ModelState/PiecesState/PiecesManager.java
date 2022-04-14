package oogasalad.Editor.ModelState.PiecesState;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;

public class PiecesManager {

  private ArrayList<LibraryPiece> availablePieces;

  public PiecesManager(){
    availablePieces = new ArrayList<>();
  }

  public LibraryPiece createPiece(int points, MovementGrid movementGrid, String pieceID, String pieceName, int teamNumber, Image image) {
    LibraryPiece newPiece = new LibraryPiece(points, movementGrid, pieceID, pieceName, teamNumber, image);
    availablePieces.add(newPiece);
    return newPiece;
  }

  public void changePieceImage(String pieceID, Image pieceImage){
    findPiece(pieceID).setPieceImage(pieceImage);
  }

  public void changePieceMovement(String pieceID, MovementGrid movementGrid){
    findPiece(pieceID).setPieceMovement(movementGrid);
  }

  public LibraryPiece getPiece(String pieceID){
    return findPiece(pieceID);
  }

  public List<LibraryPiece> getAllPieces(){
    return availablePieces;
  }

  public void setPieceName(String pieceID, String name){
    findPiece(pieceID).setPieceName(name);
  }

  public void setPiecePointValue(String pieceID, int points){
    findPiece(pieceID).setPointValue(points);
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
