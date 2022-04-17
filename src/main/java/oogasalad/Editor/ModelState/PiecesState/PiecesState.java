package oogasalad.Editor.ModelState.PiecesState;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;

public class PiecesState {
  private ArrayList<EditorPiece> availablePieces;

  public PiecesState(){
    availablePieces = new ArrayList<>();
  }

  public EditorPiece getPiece(String pieceID) {
    return findPiece(pieceID);
  }

  public List<EditorPiece> getAllPieces(){
    return availablePieces;
  }

  public void changePieceImage(String pieceID, Image imageFile, int team) {
    findPiece(pieceID).setImage(team, imageFile);
  }

  public EditorPiece createCustomPiece(String pieceID) {
    EditorPiece newPiece = new EditorPiece(pieceID);
    availablePieces.add(newPiece);
    return newPiece;
  }

  public void setPiecePointValue(String pieceID, int points){
    findPiece(pieceID).setPointValue(points);
  }

  public void setPieceName(String pieceID, String name){
    findPiece(pieceID).setPieceName(name);
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
