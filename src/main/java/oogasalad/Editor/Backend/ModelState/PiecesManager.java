package oogasalad.Editor.Backend.ModelState;

import java.util.ArrayList;
import java.util.List;

public class PiecesManager {

  private List<EditorPiece> availablePieces;

  public PiecesManager(){
    availablePieces = new ArrayList<>();
  }

  public EditorPiece createPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName) {
    EditorPiece newPiece = new EditorPiece(points, teamNumber, image, movementRules, pieceID, pieceName);
    availablePieces.add(newPiece);
    return newPiece;
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

  public void setPieceName(String pieceID, String name){
    findPiece(pieceID).setPieceName(name);
  }

  public void setPiecePointValue(String pieceID, int points){
    findPiece(pieceID).setPointValue(points);
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
