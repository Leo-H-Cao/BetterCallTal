package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionTest {

  static final String PROMOTE_TEST_FILE = BOARD_TEST_FILES_HEADER + "PromotionTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(PROMOTE_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void promoteTest() {
    try {
      Piece pawn = myBoard.getTile(Coordinate.of(1, 0)).getPiece().get();
      myBoard.move(pawn, Coordinate.of(0 ,0));
      assertEquals("Rook", myBoard.getTile(Coordinate.of(0, 0)).getPiece().get().getName());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}