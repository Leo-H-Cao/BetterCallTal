package oogasalad.GamePlayer.Movement.MovementModifiers;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveAbsorptionTest {
  static final String MA_TEST_FILE = BOARD_TEST_FILES_HEADER + "MoveAbsorptionTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(MA_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testAbsorption() throws Throwable {
    try {
      Piece pawn = myBoard.getTile(Coordinate.of(6, 0)).getPiece().get();
      myBoard.move(pawn, Coordinate.of(5, 1));
      assertFalse(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(6, 1))));
      myBoard.move(pawn, Coordinate.of(4, 2));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(6, 1))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(5, 0))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(3, 0))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(2, 1))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(2, 3))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(3, 4))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(5, 4))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(6, 3))));
      assertTrue(pawn.getMoves(myBoard).contains(myBoard.getTile(Coordinate.of(3, 2))));
    } catch (Throwable e) {
      fail();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}