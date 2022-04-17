package oogasalad.Editor.ModelState.PiecesState;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import java.util.Objects;

public class PiecesState {
  private SimpleListProperty<EditorPiece> availablePieces;

  public PiecesState(){
    availablePieces = new SimpleListProperty<>(FXCollections.observableArrayList());
  }

  public EditorPiece getPiece(String pieceID) {
    return findPiece(pieceID);
  }

  public SimpleListProperty<EditorPiece> getAllPieces(){
    return availablePieces;
  }

  public void changePieceImage(String pieceID, Image imageFile, int team) {
    Objects.requireNonNull(findPiece(pieceID)).setImage(team, imageFile);
  }

  public EditorPiece createCustomPiece(String pieceID) {
    EditorPiece newPiece = new EditorPiece(pieceID);
    availablePieces.getValue().add(newPiece);
    return newPiece;
  }

  public void setPiecePointValue(String pieceID, int points){
    findPiece(pieceID).setPointValue(points);
  }

  public void setPieceName(String pieceID, String name){
    findPiece(pieceID).setPieceName(name);
  }

  private EditorPiece findPiece(String pieceID){
    for(EditorPiece piece : availablePieces.getValue()){
      if(piece.getPieceID().equals(pieceID)){
        return piece;
      }
    }
    return null;
  }
}
