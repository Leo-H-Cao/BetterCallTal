package oogasalad.Editor.ModelState.PiecesState;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditPieceGrid;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;

public class PiecesManager {

  private ArrayList<LibraryPiece> availablePieces;

  public PiecesManager(){
    availablePieces = new ArrayList<>();
  }

  public LibraryPiece createPiece(int points, EditPieceGrid editPieceGrid, String pieceID, String pieceName, int teamNumber, Image image) {
    LibraryPiece newPiece = new LibraryPiece(points, editPieceGrid, pieceID, pieceName, teamNumber, image);
    availablePieces.add(newPiece);
    return newPiece;
  }

  public void changePieceImage(String pieceID, Image pieceImage){
    findPiece(pieceID).setPieceImage(pieceImage);
  }

  public void changePieceMovement(String pieceID, EditPieceGrid editPieceGrid){
    findPiece(pieceID).setPieceMovement(editPieceGrid);
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
