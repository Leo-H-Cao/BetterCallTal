package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckersCaptureTest {

  public static final String CHECKERS_TEST_FILE = BOARD_TEST_FILES_HEADER + "CheckersTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(CHECKERS_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testCaptures() {
    try {
      Piece redPieceOne = myBoard.getTile(Coordinate.of(6, 3)).getPiece().get();
      Piece redPieceTwo = myBoard.getTile(Coordinate.of(4, 2)).getPiece().get();
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(4,5)), myBoard.getTile(Coordinate.of(2,3))),
          redPieceOne.getMoves(myBoard));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(2,0)), myBoard.getTile(Coordinate.of(2,4)),
              myBoard.getTile(Coordinate.of(0,6))), redPieceTwo.getMoves(myBoard));
      assertThrows(InvalidMoveException.class, () ->
          new CheckersCapture().capturePiece(redPieceTwo, Coordinate.of(0, 0), myBoard));
      myBoard.move(redPieceTwo, Coordinate.of(0, 6));
      assertEquals("checkerKing", myBoard.getTile(Coordinate.of(0, 6)).getPiece().get().getName());
      assertTrue(myBoard.getTile(Coordinate.of(3,3)).getPieces().isEmpty());
      assertTrue(myBoard.getTile(Coordinate.of(1,5)).getPieces().isEmpty());
    } catch (Throwable e) {
      fail();
    }
  }

  @Test
  void testMoves() {
    try {
      assertEquals(Set.of(), new CheckersCapture().getMoves(null, null));
      assertThrows(InvalidMoveException.class, () -> new CheckersCapture().movePiece(null, null, null));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void checkersCaptureEqualsTest() {
    assertEquals(new CheckersCapture(), new CheckersCapture());
    assertNotEquals(new CheckersCapture(), null);
  }
}