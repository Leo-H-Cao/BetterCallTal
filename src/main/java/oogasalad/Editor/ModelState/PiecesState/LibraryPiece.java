package oogasalad.Editor.ModelState.PiecesState;

import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;

public class LibraryPiece {

  private MovementGrid movementGrid;
  private int pointValue;
  private String pieceID;
  private String pieceName;
  private int teamNumber;
  private Image image;
  private boolean mainPiece;
  private EditorPiece editorPiece;

  public LibraryPiece(int points, EditorPiece editorPiece, String pieceName, int teamNumber, Image image){
    this.pointValue = points;
    this.pieceName = pieceName;
    this.teamNumber = teamNumber;
    this.image = image;
    this.editorPiece = editorPiece;
  }

  public LibraryPiece(LibraryPiece piece){
    this.pointValue = piece.getPointValue();
    this.pieceName = piece.getPieceName();
    this.teamNumber = piece.getTeamNumber();
    this.image = piece.getImage();
    this.editorPiece = piece.getEditorPiece();
  }

  public String getPieceID(){
    return editorPiece.getPieceID();
  }

  public Image getImage(){return image; }

  public MovementGrid getPieceMovement() {
    return editorPiece.getMovementGrid();
  }

  public int getPointValue() {
    return pointValue;
  }

  public String getPieceName() {
    return pieceName;
  }

  public int getTeamNumber() {
    return teamNumber;
  }

  public boolean isMainPiece() {
    return editorPiece.getMainPiece();
  }

  public EditorPiece getEditorPiece() {
    return editorPiece;
  }

  public void setPointValue(int pointValue) {
    this.pointValue = pointValue;
  }

  public void setPieceName(String pieceName) {
    this.pieceName = pieceName;
  }

  public void setPieceImage(Image image) {
    this.image = image;
  }

  public ArrayList<String> getCustomMoves(){
    return editorPiece.getCustomMoves();
  }

  public void updatePiece(){
    this.movementGrid = editorPiece.getMovementGrid();
    this.pieceID = editorPiece.getPieceID();
    this.mainPiece = editorPiece.getMainPiece();
  }
}
