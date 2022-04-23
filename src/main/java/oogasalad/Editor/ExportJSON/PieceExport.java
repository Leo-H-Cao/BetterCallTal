package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;

public class PieceExport {
  private String pieceName;
  private String imgFile;
  private int pointValue;
  private ArrayList<String> customMoves;
  private ArrayList<String> movementModifiers;
  private ArrayList<ArrayList<String>> onInteractionModifier;
  private ArrayList<String> basicMovements;
  private ArrayList<String> basicCaptures;


  public PieceExport(EditorPiece editorPiece, int team){
    pieceName = editorPiece.getPieceName();
    imgFile = editorPiece.getImage(team).getValue().getUrl().split("/classes/")[1];
    pointValue = editorPiece.getPointValue();
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    basicMovements = new ArrayList<>();
    basicCaptures = new ArrayList<>();
    basicMovements.add(pieceName+"Movement");

    //change for capture != movement
    basicCaptures.add(pieceName+"Movement");
    movementModifiers = new ArrayList<>();
    onInteractionModifier = editorPiece.getOnInteractionModifiers();
  }

  public String getPieceName() {
    return pieceName;
  }

  public String getImgFile() {
    return imgFile;
  }

  public int getPointValue() {
    return pointValue;
  }

  public ArrayList<String> getCustomMoves() {
    return customMoves;
  }

  public ArrayList<String> getMovementModifiers() {
    return movementModifiers;
  }

  public ArrayList<ArrayList<String>> getOnInteractionModifier() {
    return onInteractionModifier;
  }

  public ArrayList<String> getBasicMovements() {
    return basicMovements;
  }

  public ArrayList<String> getBasicCaptures() {
    return basicCaptures;
  }
}
