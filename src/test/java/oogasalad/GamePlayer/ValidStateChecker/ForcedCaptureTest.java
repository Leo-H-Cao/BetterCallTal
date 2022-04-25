package oogasalad.GamePlayer.ValidStateChecker;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ForcedCaptureTest {

  static final String FORCED_CAPTURE_FILE = BOARD_TEST_FILES_HEADER + "AntichessModified.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(FORCED_CAPTURE_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testValidCheck() {
    try {
      Piece pawnOne = myBoard.getTile(Coordinate.of(2, 2)).getPiece().get();
      Piece pawnTwo = myBoard.getTile(Coordinate.of(4, 4)).getPiece().get();
      assertTrue(myBoard.getMoves(pawnTwo).isEmpty());
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(1, 1))), myBoard.getMoves(pawnOne));
      myBoard.move(pawnOne, Coordinate.of(1, 1));
      assertFalse(myBoard.getMoves(pawnTwo).isEmpty());
      assertFalse(myBoard.getMoves(pawnOne).isEmpty());
    } catch (Exception e) {
      fail();
    }
  }
}