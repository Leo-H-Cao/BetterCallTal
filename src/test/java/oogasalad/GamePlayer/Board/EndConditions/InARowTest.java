package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InARowTest {

  public static final String IAR_TEST_FILE = BOARD_TEST_FILES_HEADER + "TicTacToeTwo.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(IAR_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void inARowTest() {
    try {
      assertEquals(Collections.emptyMap(), new InARow().getScores(myBoard));
      myBoard.move(myBoard.getTile(Coordinate.of(2, 4)).getPiece().get(), Coordinate.of(0, 2));
      assertEquals(Map.of(0, 1.0, 1, 0.0), new InARow().getScores(myBoard));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void configFileTest() {
    InARow test = new InARow("bad file");
    assertEquals(3, test.getNumInARow());
    assertEquals(List.of("checker"), test.getPieceNames());
  }
}