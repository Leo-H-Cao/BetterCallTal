package oogasalad.Editor.ModelState.PiecesState;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import java.util.Objects;

/**
 * Class that manages pieces that have been created,
 * tracks changes to existing pieces made by user
 * @author Leo Cao
 */
public class PiecesState {
  private final SimpleListProperty<EditorPiece> availablePieces;

  public PiecesState(){
    availablePieces = new SimpleListProperty<>(FXCollections.observableArrayList());
  }

  public EditorPiece getPiece(String pieceID) {
    return findPiece(pieceID);
  }

  public SimpleListProperty<EditorPiece> getAllPieces(){
    return availablePieces;
  }

  /**
   * Changes piece image to image set by user
   * @param pieceID identifier of the piece whose image is to be changed
   * @param team determine which team's (black or white) image of this piece should be changed
   * @param imageFile new piece image
   */
  public void changePieceImage(String pieceID, Image imageFile, int team) {
    Objects.requireNonNull(findPiece(pieceID)).setImage(team, imageFile);
  }

  /**
   * Creates a default EditorPiece
   * @param pieceId new unique pieceID for piece that is being created
   * @return created piece
   */
  public EditorPiece createDefaultPiece(String pieceId) {
    EditorPiece newPiece = new EditorPiece(pieceId, true);
    availablePieces.getValue().add(newPiece);
    return newPiece;
  }

  /**
   * Creats custom piece with configurations set by user
   * @param pieceID new unique pieceID for piece that is being created
   * @return created piece
   */
  public EditorPiece createCustomPiece(String pieceID) {
    EditorPiece newPiece = new EditorPiece(pieceID);
    availablePieces.getValue().add(newPiece);
    return newPiece;
  }

  /**
   * Changes point value of certain piece
   * @param pieceID identifier for piece that is being modified
   * @param points number of points piece is worth
   */
  public void setPiecePointValue(String pieceID, int points){
    Objects.requireNonNull(findPiece(pieceID)).setPointValue(points);
  }

  /**
   * Changes name of certain existing piece
   * @param pieceID identifier for piece that is being modified
   * @param name new name of piece
   */
  public void setPieceName(String pieceID, String name){
    Objects.requireNonNull(findPiece(pieceID)).setPieceName(name);
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
