package oogasalad.GamePlayer.ValidStateChecker;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.EngineExceptions.InvalidBoardSizeException;
import org.junit.jupiter.api.Test;

class BankBlockerTest {

  static final String BANK_BLOCKER_TEST_FILE = "TicTacToeConfig";
  static final String BANK_BLOCKER_INVALID_SIZE =
      BOARD_TEST_FILES_HEADER + "CrazyhouseBankTooSmall.json";

  @Test
  void testConfigFiles() {
    assertEquals(8, new BankBlocker().blockCol);
    assertEquals(3, new BankBlocker(BANK_BLOCKER_TEST_FILE).blockCol);
    assertEquals(8, new BankBlocker("fidsaf87").blockCol);
  }

  @Test
  void testInvalidSize() {
    try {
      ChessBoard myBoard = BoardSetup.createLocalBoard(BANK_BLOCKER_INVALID_SIZE);
      assertThrows(InvalidBoardSizeException.class,
          () -> new BankBlocker().isValid(myBoard, null, null));
    } catch (Exception e) {
      fail();
    }
  }
}
