package oogasalad.Editor.Backend.ModelState;

import java.util.ArrayList;
import java.util.List;

public class PiecesManager {

  private List<EditorPiece> availablePieces;

  public PiecesManager(){
    availablePieces = new ArrayList<>();
  }

  public EditorPiece createPiece(int points, MovementRules movementRules, String pieceID, String pieceName, PieceInfo pieceInfo) {
    EditorPiece newPiece = new EditorPiece(points, movementRules, pieceID, pieceName, pieceInfo);
    availablePieces.add(newPiece);
    return newPiece;
  }

  public void changePieceImage(String pieceID, String pieceImage){
    PieceInfo pieceInfo = findPiece(pieceID).getPieceInfo();
    pieceInfo.setImage(pieceImage);
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

  public void setStartingLocation(String pieceID, int startX, int startY){
    PieceInfo pieceInfo = findPiece(pieceID).getPieceInfo();
    pieceInfo.setStartingPosX(startX);
    pieceInfo.setStartingPosY(startY);
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
