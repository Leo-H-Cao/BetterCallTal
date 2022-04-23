package oogasalad.GamePlayer.Movement.MovementModifiers;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GravityMovementModifierTest {

  static final String GRAVITY_MM_TEST_FILE = BOARD_TEST_FILES_HEADER + "Gravity.json";
  static final String GRAVITY_MM_FILE_TWO = BOARD_TEST_FILES_HEADER + "GravityTwo.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(GRAVITY_MM_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testConfigFiles() {
    try {
      GravityMovementModifier test = new GravityMovementModifier("89y473hf");
      assertEquals(List.of(Coordinate.of(0, 1)), test.getRelativeCoordinates());

      test = new GravityMovementModifier();
      assertEquals(List.of(Coordinate.of(0, 1)), test.getRelativeCoordinates());

      test = new GravityMovementModifier("GravityDouble");
      assertEquals(List.of(Coordinate.of(0, 1), Coordinate.of(-1, 0)), test.getRelativeCoordinates());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testDissapear() {
    try {
      myBoard.move(myBoard.getTile(Coordinate.of(6, 6)).getPiece().get(), Coordinate.of(5, 6));
      assertTrue(myBoard.getTile(Coordinate.of(6, 0)).getPiece().isEmpty());

      myBoard = BoardSetup.createLocalBoard(GRAVITY_MM_FILE_TWO);
      myBoard.move(myBoard.getTile(Coordinate.of(6, 4)).getPiece().get(), Coordinate.of(5, 4));
      assertTrue(myBoard.getTile(Coordinate.of(6, 0)).getPiece().isEmpty());
      assertTrue(myBoard.getTile(Coordinate.of(2, 7)).getPiece().isPresent());
    } catch (Exception e) {
      fail();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}