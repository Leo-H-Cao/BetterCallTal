package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoopAroundTest {

  static final String LOOP_AROUND_FILE = BOARD_TEST_FILES_HEADER + "ChutesAndLadders.json";
  static final String LOOP_AROUND_FILE_MODIFIED = BOARD_TEST_FILES_HEADER + "ChutesAndLaddersModified.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(LOOP_AROUND_FILE_MODIFIED);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testMoves() {
    try {
      assertEquals(Collections.emptyList(), new LoopAround().getRelativeCoords());
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(2, 1))),
          myBoard.getMoves(myBoard.getTile(Coordinate.of(3,0)).getPiece().get()));
      myBoard.move(myBoard.getTile(Coordinate.of(3,0)).getPiece().get(), Coordinate.of(2, 1));
      assertTrue(myBoard.getTile(Coordinate.of(3, 0)).getPiece().isEmpty());
      assertTrue(myBoard.getTile(Coordinate.of(2, 1)).getPiece().isPresent());
      assertEquals(Set.of(), myBoard.getMoves(myBoard.getTile(Coordinate.of(0, 0)).getPiece().get()));
      assertThrows(InvalidMoveException.class, () -> new LoopAround().movePiece(
          myBoard.getTile(Coordinate.of(0, 0)).getPiece().get(), Coordinate.of(1, 0), myBoard));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testConfigFiles() {
    try {
      LoopAround test = new LoopAround();
      assertEquals(Coordinate.of(0, 1), test.getPMD());
      assertEquals(Coordinate.of(-1, 0), test.getSMD());

      test = new LoopAround("8765fdsa");
      assertEquals(Coordinate.of(0, 1), test.getPMD());
      assertEquals(Coordinate.of(-1, 0), test.getSMD());

      test = new LoopAround("MoveUp2Loop2");
      assertEquals(Coordinate.of(0, 2), test.getPMD());
      assertEquals(Coordinate.of(-2, 0), test.getSMD());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testCaptures() {
    try {
      assertEquals(Collections.emptySet(), new LoopAround().getCaptures(null, null));
      assertThrows(InvalidMoveException.class, () ->
          new LoopAround().capturePiece(null, null, null));
    } catch (Exception e) {
      fail();
    }
  }
}