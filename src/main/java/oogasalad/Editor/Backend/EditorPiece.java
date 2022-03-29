package oogasalad.Editor.Backend;

public class EditorPiece {
  private final String DEFAULT_PIECE_IMAGE = "";
  private final int DEFAULT_POINT_VALUE = 1;

  private MovementRules myMovementRules;
  private String pieceImage;
  private int pointValue;
  private int team;
  private String myPieceID;

  public EditorPiece(){
    pieceImage = DEFAULT_PIECE_IMAGE;
    pointValue = DEFAULT_POINT_VALUE;
  }

  public EditorPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID){
    myMovementRules = movementRules;
    pointValue = points;
    team = teamNumber;
    pieceImage = image;
    myPieceID = pieceID;
  }

  public void setPieceImage(String pieceImageFile){
    pieceImage = pieceImageFile;
  }

  public String getPieceID(){
    return myPieceID;
  }

  public void setPieceMovement(MovementRules movementRules){
    myMovementRules = movementRules;
  }

}
