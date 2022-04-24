package oogasalad.Editor.ExportJSON;

/**
 * Basic movement class designed to be serialized to JSON by Jackson library
 * @author Leo Cao
 */
public class BasicMovementExport {
  private int[] relativeCoords;
  private boolean infinite;

  public BasicMovementExport(int relX, int relY, Boolean infinite){
    relativeCoords = new int[2];
    relativeCoords[0] = relX;
    relativeCoords[1] = relY;
    this.infinite = infinite;
  }

  public int[] getRelativeCoords() {
    return relativeCoords;
  }

  public boolean isInfinite() {
    return infinite;
  }
}
