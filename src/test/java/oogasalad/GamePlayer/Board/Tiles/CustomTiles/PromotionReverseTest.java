package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionReverseTest {

  static final String PR_TEST_FILE = BOARD_TEST_FILES_HEADER + "PRTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(PR_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void prTest() {
    try {
      Piece checker = myBoard.getTile(Coordinate.of(1, 1)).getPiece().get();
      Piece rook = myBoard.getTile(Coordinate.of(4, 4)).getPiece().get();

      myBoard.move(checker, Coordinate.of(0 ,2));
      assertEquals("checkerKing", checker.getName());
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(1, 1)),
          myBoard.getTile(Coordinate.of(1, 3))), checker.getMoves(myBoard));

      myBoard.move(rook, Coordinate.of(0 ,4));
      assertEquals("Rook", rook.getName());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void badConfigFileTest() {
    PromotionReverse test = new PromotionReverse("bad file");
    assertEquals(List.of("checker", "pawn"), test.getPromotablePieceNames());
    assertEquals("checkerKing", test.getPromotionImage());
  }
}