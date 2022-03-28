package oogasalad.GamePlayer.Movement;

public class Coordinate{

  private int row;
  private int col;

  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public static Coordinate add(Coordinate one, Coordinate two) {
    return new Coordinate(one.row + two.row, one.col + two.col);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public String toString() {
    return row + ", " + col;
  }
}
