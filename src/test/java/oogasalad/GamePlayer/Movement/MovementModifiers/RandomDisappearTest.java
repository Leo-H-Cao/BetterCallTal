package oogasalad.GamePlayer.Movement.MovementModifiers;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomDisappearTest {

  static final String RANDOM_DISAPPEAR_TEST_FILE = BOARD_TEST_FILES_HEADER + "RemoveAfterMoveAlways.json";
  static final String RANDOM_DISAPPEAR_TEST_FILE_TWO = BOARD_TEST_FILES_HEADER + "RemoveAfterMoveNever.json";


  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(RANDOM_DISAPPEAR_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testConfigFiles() {
    try {
      RandomDisappear test = new RandomDisappear("89y473hf");
      assertEquals(0.05, test.getChanceOfDisappearing());

      test = new RandomDisappear();
      assertEquals(0.05, test.getChanceOfDisappearing());

      test = new RandomDisappear("RDOne");
      assertEquals(1.0, test.getChanceOfDisappearing());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testDissapear() {
    try {
      myBoard.move(myBoard.getTile(Coordinate.of(6, 0)).getPiece().get(), Coordinate.of(5, 0));
      assertTrue(myBoard.getTile(Coordinate.of(5, 0)).getPiece().isEmpty());

      myBoard = BoardSetup.createLocalBoard(RANDOM_DISAPPEAR_TEST_FILE_TWO);
      myBoard.move(myBoard.getTile(Coordinate.of(6, 0)).getPiece().get(), Coordinate.of(5, 0));
      assertTrue(myBoard.getTile(Coordinate.of(5, 0)).getPiece().isPresent());
    } catch (Exception e) {
      fail();
    }
  }
}