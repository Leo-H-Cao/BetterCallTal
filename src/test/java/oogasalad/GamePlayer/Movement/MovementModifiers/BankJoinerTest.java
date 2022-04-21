package oogasalad.GamePlayer.Movement.MovementModifiers;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BANK_TEST_FILE;
import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankJoinerTest {

  static final String BANK_JOINER_TEST_FILE = BOARD_TEST_FILES_HEADER + "CrazyhouseModifiedTwo.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(BANK_JOINER_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testConfigFiles() {
    try {
      BankJoiner test = new BankJoiner("89y473hf");
      assertEquals(8, test.getBlockCol());

      test = new BankJoiner("TicTacToeConfig");
      assertEquals(3, test.getBlockCol());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testBankFilling() {
    try {
      Piece rook = myBoard.getTile(Coordinate.of(0, 0)).getPiece().get();
      myBoard.move(rook, Coordinate.of(0, 1));
      assertTrue(myBoard.getTile(Coordinate.of(7, 9)).getPiece().isPresent());

      myBoard.move(rook, Coordinate.of(0, 2));
      assertTrue(myBoard.getTile(Coordinate.of(7, 10)).getPiece().isPresent());

      myBoard.move(rook, Coordinate.of(0, 3));
      assertTrue(myBoard.getTile(Coordinate.of(6, 9)).getPiece().isPresent());
    } catch (Exception e) {
      fail();
    }
  }
}