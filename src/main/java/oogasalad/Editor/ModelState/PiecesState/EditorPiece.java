package oogasalad.Editor.ModelState.PiecesState;

import javafx.scene.image.Image;

public class EditorPiece {

  private MovementRules myMovementRules;
  private int pointValue;
  private String myPieceID;
  private String myPieceName;
  private int myTeamNumber;
  private Image myImage;

  public EditorPiece(int points, MovementRules movementRules, String pieceID, String pieceName, int teamNumber, Image image ){
    myMovementRules = movementRules;
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

  public MovementRules getMovementRules() {
    return myMovementRules;
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

  public void setPieceMovement(MovementRules movementRules){
    myMovementRules = movementRules;
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
