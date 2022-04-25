package oogasalad.Editor.ModelState.PiecesState;

/**
 * Coordinate representation for a piece
 * @author Leo Cao
 */
public class EditorCoordinate {
  private final int x;
  private final int y;

  /***
   * Creates a new coordinate object with row, col
   * @param x of coordinate
   * @param y of coordinate
   */
  public EditorCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
