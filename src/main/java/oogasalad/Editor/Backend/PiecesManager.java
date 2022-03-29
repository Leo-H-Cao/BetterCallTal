package oogasalad.Editor.Backend;

import java.util.ArrayList;
import java.util.List;

public class PiecesManager {

  private List<EditorPiece> availablePieces;

  public PiecesManager(){
    availablePieces = new ArrayList<>();
  }

  public void createPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID) {
    EditorPiece newPiece = new EditorPiece(points, teamNumber, image, movementRules, pieceID);
    availablePieces.add(newPiece);
  }

  public void changePieceImage(String pieceID, String pieceImage){
    findPiece(pieceID).setPieceImage(pieceImage);
  }

  public void changePieceMovement(String pieceID, MovementRules movementRules){
    findPiece(pieceID).setPieceMovement(movementRules);

  }

  public EditorPiece getPiece(String pieceID){
    return findPiece(pieceID);
  }

  private EditorPiece findPiece(String pieceID){
    for(EditorPiece piece : availablePieces){
      if(piece.getPieceID().equals(pieceID)){
        return piece;
      }
    }
    return null;
  }

}
