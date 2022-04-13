package oogasalad.GamePlayer.GamePiece;

public class PieceHealth {

  private static final int DEFAULT_HEALTH = 5;

  private int health;

  public PieceHealth() {
    health = DEFAULT_HEALTH;
  }

  public PieceHealth(int i) {
    health = i;
  }

  public boolean damage() {
    health -= 1;
    return health >= 0;
  }

}
