package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

/**
 * Collection of basic movement objects (to fit format of game engine) designed to be serialized to JSON by Jackson library
 * @author Leo Cao
 */
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
