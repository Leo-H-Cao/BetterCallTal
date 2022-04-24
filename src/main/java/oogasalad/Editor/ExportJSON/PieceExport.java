package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;

/**
 * Class containing info for single piece file that is to be exported, separate from piece
 * information that goes in the main export file
 * @author Leo Cao
 */
public class PieceExport {
  private String pieceName;
  private String imgFile;
  private int pointValue;
  private ArrayList<String> customMoves;
  private ArrayList<String> movementModifiers;
  private ArrayList<ArrayList<String>> onInteractionModifier;
  private ArrayList<String> basicMovements;
  private ArrayList<String> basicCaptures;


  public PieceExport(EditorPiece editorPiece, int teamNum){
    pieceName = editorPiece.getPieceName().getValue();
    imgFile = editorPiece.getImage(teamNum).getValue().getUrl().split("/classes/")[1];
    pointValue = editorPiece.getPointValue();
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    basicMovements = new ArrayList<>();
    basicCaptures = new ArrayList<>();

    String team = teamNum == 0? "w" : "b";
    basicMovements.add(team+pieceName+"Movement");

    //change for capture != movement
    basicCaptures.add(team+pieceName+"Movement");
    movementModifiers = new ArrayList<>();
    onInteractionModifier = editorPiece.getOnInteractionModifiers();

    team = teamNum == 0? "White" : "Black";
    pieceName = team+editorPiece.getPieceName().getValue();
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
