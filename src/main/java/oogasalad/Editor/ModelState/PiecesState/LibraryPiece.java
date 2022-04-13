package oogasalad.Editor.ModelState.PiecesState;

import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;

public class LibraryPiece {

  private MovementGrid movementGrid;
  private int pointValue;
  private String myPieceID;
  private String myPieceName;
  private int myTeamNumber;
  private Image myImage;

  public LibraryPiece(int points, MovementGrid movementGrid, String pieceID, String pieceName, int teamNumber, Image image ){
    this.movementGrid = movementGrid;
    pointValue = points;
    myPieceID = pieceID;
    myPieceName = pieceName;
    myTeamNumber = teamNumber;
    myImage = image;
  }

  public String getPieceID(){
    return myPieceID;
  }

  public Image getImage(){
    return myImage;
  }

  public MovementGrid getPieceMovement() {
    return movementGrid;
  }

  public int getPointValue() {
    return pointValue;
  }

  public String getPieceName() {
    return myPieceName;
  }

  public int getTeamNumber() {
    return myTeamNumber;
  }

  public void setTeamNumber(int myTeamNumber) {
    this.myTeamNumber = myTeamNumber;
  }

  public void setPieceMovement(MovementGrid movementGrid){
    this.movementGrid = movementGrid;
  }

  public void setPointValue(int points){
    pointValue = points;
  }

  public void setPieceName(String name){
    myPieceName = name;
  }

  public void setPieceImage(Image image){
    myImage = image;
  }


}
