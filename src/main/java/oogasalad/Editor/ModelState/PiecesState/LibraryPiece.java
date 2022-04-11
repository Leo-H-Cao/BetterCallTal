package oogasalad.Editor.ModelState.PiecesState;

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

  public LibraryPiece(int points, EditorPiece editorPiece, String pieceName, int teamNumber, Image image){
    this.movementGrid = editorPiece.getMovementGrid();
    this.pointValue = points;
    this.pieceID = editorPiece.getPieceID();
    this.pieceName = pieceName;
    this.teamNumber = teamNumber;
    this.image = image;
    this.mainPiece = editorPiece.getMainPiece();
  }

  public String getPieceID(){
    return pieceID;
  }

  public Image getImage(){
    return image;
  }

  public MovementGrid getPieceMovement() {
    return movementGrid;
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

  public void setTeamNumber(int teamNumber) {
    this.teamNumber = teamNumber;
  }

  public void setPieceMovement(MovementGrid movementGrid){
    this.movementGrid = movementGrid;
  }

  public void setPointValue(int points){
    pointValue = points;
  }

  public void setPieceName(String name){
    pieceName = name;
  }

  public void setPieceImage(Image image){
    this.image = image;
  }


}
