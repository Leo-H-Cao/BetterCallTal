package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;

public class PieceMainExport {
  private int row;
  private int col;
  private int team;
  private int mainPiece;
  private String pieceFile;
  private ArrayList<String> movementModifiers;
  private ArrayList<String> onInteractionModifier;
  private ArrayList<String> customMoves;

  public PieceMainExport(int row, int col, int team, EditorPiece editorPiece){
    this.row = row;
    this.col = col;
    this.team = team;
    this.mainPiece = editorPiece.isMainPiece() ? 1 : 0;
    movementModifiers = new ArrayList<>();
    onInteractionModifier = new ArrayList<>();
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    pieceFile = editorPiece.getPieceName();
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
