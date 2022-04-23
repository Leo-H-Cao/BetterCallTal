package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

public class BasicMovementExportWrapper {
  ArrayList<BasicMovementExport> moves;

  public BasicMovementExportWrapper(){
    moves = new ArrayList<>();
  }

  public void addMovement(BasicMovementExport basicMovementExport){
    moves.add(basicMovementExport);
  }

  public ArrayList<BasicMovementExport> getMoves() {
    return moves;
  }
}
