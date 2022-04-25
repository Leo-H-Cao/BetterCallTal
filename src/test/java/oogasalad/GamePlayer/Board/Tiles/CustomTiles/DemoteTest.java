package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemoteTest {

  static final String DEMOTE_TEST_FILE = BOARD_TEST_FILES_HEADER + "DemoteTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(DEMOTE_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void demoteTest() {
    try {
      Piece rook = myBoard.getTile(Coordinate.of(7, 0)).getPiece().get();
      myBoard.move(rook, Coordinate.of(7 ,3));
      assertEquals("Pawn", myBoard.getTile(Coordinate.of(7, 3)).getPiece().get().getName());
    } catch (Exception e) {
      fail();
    }
  }
}