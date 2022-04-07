package oogasalad.Editor.ModelState.PiecesState;

public class EditorCoordinate {
  private int x;
  private int y;

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
