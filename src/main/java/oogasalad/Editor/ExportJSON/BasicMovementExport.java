package oogasalad.Editor.ExportJSON;

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
