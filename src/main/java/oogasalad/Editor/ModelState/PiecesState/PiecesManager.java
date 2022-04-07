package oogasalad.Editor.ModelState.PiecesState;

import java.util.ArrayList;
import java.util.List;

public class PiecesManager {

  private ArrayList<EditorPiece> availablePieces;

  public PiecesManager(){
    availablePieces = new ArrayList<>();
  }

  public EditorPiece createPiece(int points, MovementRules movementRules, String pieceID, String pieceName, int teamNumber, String image) {
    EditorPiece newPiece = new EditorPiece(points, movementRules, pieceID, pieceName, teamNumber, image);
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

  public List<EditorPiece> getAllPieces(){
    return availablePieces;
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
