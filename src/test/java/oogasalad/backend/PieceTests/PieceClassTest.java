package oogasalad.backend.PieceTests;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifier;
import oogasalad.GamePlayer.Movement.MovementSetModifier;
import oogasalad.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceClassTest {

  private ChessBoard board;
  @BeforeEach
  void setup() {
    Main m = new Main();
  }

  /**
   * Checks to see if a piece moves correctly to a valid tile based on a players move
   */
  @Test
  void movedIntoValidTile() {

  }

  /**
   * Checks to see if somehow a person tries to move to an invalid tile, the piece does not respond.
   */
  @Test
  void movedIntoInvalidTile() {

  }
}
