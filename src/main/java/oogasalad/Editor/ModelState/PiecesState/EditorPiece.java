package oogasalad.Editor.ModelState.PiecesState;

public class EditorPiece {
  private final String DEFAULT_PIECE_IMAGE = "";
  private final int DEFAULT_POINT_VALUE = 1;

  private MovementRules myMovementRules;
  private int pointValue;
  private String myPieceID;
  private String myPieceName;
  private PieceInfo myPieceInfo;

  public EditorPiece(int points, MovementRules movementRules, String pieceID, String pieceName, PieceInfo pieceInfo){
    myMovementRules = movementRules;
    pointValue = points;
    myPieceID = pieceID;
    myPieceName = pieceName;
    myPieceInfo = pieceInfo;
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

  public PieceInfo getPieceInfo(){
    return myPieceInfo;
  }


}
