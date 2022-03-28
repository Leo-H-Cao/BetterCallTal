package oogasalad.GamePlayer.Movement;

public record Coordinate(int row, int col){
  @Override
  public String toString() {
    return row + ", " + col;
  }
}
