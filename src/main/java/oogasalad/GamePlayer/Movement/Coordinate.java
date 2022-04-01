package oogasalad.GamePlayer.Movement;

public class Coordinate{

  private int row;
  private int col;

  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * A static method that takes 2 Coordinate objects,
   * adds like attributes and returns a new Coordinate with the updated values
   * @param one First Coordinate Object
   * @param two Second Coordinate Object
   * @return A new Coordinate Object
   */
  public static Coordinate add(Coordinate one, Coordinate two) {
    return new Coordinate(one.row + two.row, one.col + two.col);
  }

  /**
   * Gets the attribute that corresponds to the Row
   * @return the attribute Row
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the attribute that corresponds to the Column
   * @return the attribute Col
   */
  public int getCol() {
    return col;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", row, col);
  }

  public boolean equals(Coordinate other) {
    return this.row == other.getRow() && this.col == other.getCol();
  }
}
