package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.Main;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTests {

  @Test
  void equalsTest() {
   ChessTile test = new ChessTile();
   assertEquals(test, test);
   assertNotEquals(test, null);
   assertEquals(test, new ChessTile(Coordinate.of(0, 0)));
  }
}
