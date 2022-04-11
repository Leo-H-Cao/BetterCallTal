package oogasalad.Editor.ModelState.PiecesState;

import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.EditPiece.EditPieceGrid;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;

public class LibraryPiece {

  private EditPieceGrid editPieceGrid;
  private int pointValue;
  private String myPieceID;
  private String myPieceName;
  private int myTeamNumber;
  private Image myImage;

  public LibraryPiece(int points, EditPieceGrid editPieceGrid, String pieceID, String pieceName, int teamNumber, Image image ){
    this.editPieceGrid = editPieceGrid;
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

  public EditPieceGrid getPieceMovement() {
    return editPieceGrid;
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

  public void setPieceMovement(EditPieceGrid editPieceGrid){
    this.editPieceGrid = editPieceGrid;
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
