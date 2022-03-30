package oogasalad.Editor.Backend.ModelState;

public class EditorPiece {
  private final String DEFAULT_PIECE_IMAGE = "";
  private final int DEFAULT_POINT_VALUE = 1;

  private MovementRules myMovementRules;
  private String pieceImage;
  private int pointValue;
  private int team;
  private String myPieceID;
  private String myPieceName;

  public EditorPiece(){
    pieceImage = DEFAULT_PIECE_IMAGE;
    pointValue = DEFAULT_POINT_VALUE;
  }

  public EditorPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName){
    myMovementRules = movementRules;
    pointValue = points;
    team = teamNumber;
    pieceImage = image;
    myPieceID = pieceID;
    myPieceName = pieceName;
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

  public void setPointValue(int points){
    pointValue = points;
  }

  public void setPieceName(String name){
    myPieceName = name;
  }


}
