package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;

/**
 * Class containing info for single piece file that is to be exported
 * as part of the main export file, separate from individual piece files
 * @author Leo Cao
 */
public class PieceMainExport {
  private int row;
  private int col;
  private int team;
  private int mainPiece;
  private String pieceFile;
  private ArrayList<String> movementModifiers;
  private ArrayList<String> onInteractionModifier;
  private ArrayList<String> customMoves;

  public PieceMainExport(int row, int col, int teamNum, EditorPiece editorPiece){
    String team = teamNum == 0 ? "White" : "Black";
    this.row = row;
    this.col = col;
    this.team = teamNum;
    this.mainPiece = editorPiece.isMainPiece() ? 1 : 0;
    movementModifiers = new ArrayList<>();
    onInteractionModifier = new ArrayList<>();
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    pieceFile = team+editorPiece.getPieceID();
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getTeam() {
    return team;
  }

  public int getMainPiece() {
    return mainPiece;
  }

  public String getPieceFile() {
    return pieceFile;
  }

  public ArrayList<String> getMovementModifiers() {
    return movementModifiers;
  }

  public ArrayList<String> getOnInteractionModifier() {
    return onInteractionModifier;
  }

  public ArrayList<String> getCustomMoves() {
    return customMoves;
  }
}
