package oogasalad.Frontend.GamePlayer.Movement;

public class Coordinate{

  private int row;
  private int col;

  /***
   * Creates a new coordinate object with row, col
   * @param row of coordinate
   * @param col of coordinate
   */
  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /***
   * @param row of coordinate
   * @param col of coordinate
   * @return new coordinate object with row and col
   */
  public static Coordinate of(int row, int col) {
    return new Coordinate(row, col);
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

  /***
   * @param other coordinate to compare to
   * @return if the coordinates have the same row, col
   */
  public boolean equals(Coordinate other) {
    return this.row == other.getRow() && this.col == other.getCol();
  }
}
