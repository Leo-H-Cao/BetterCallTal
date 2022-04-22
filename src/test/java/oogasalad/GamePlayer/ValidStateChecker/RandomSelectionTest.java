package oogasalad.GamePlayer.ValidStateChecker;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomSelectionTest {

  static final String RANDOM_SELECTION_FILE_ONE = BOARD_TEST_FILES_HEADER + "ChutesAndLadders.json";
  static final String RANDOM_SELECTION_FILE_TWO = BOARD_TEST_FILES_HEADER + "ChutesAndLaddersTwo.json";
  static final String RANDOM_SELECTION_FILE_THREE = BOARD_TEST_FILES_HEADER + "ChutesAndLaddersThree.json";

  private ChessBoard board;

  @BeforeEach
  void setUp() {
    try {
      board = BoardSetup.createLocalBoard(RANDOM_SELECTION_FILE_ONE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testConfigFiles() {
    assertEquals(1, new RandomSelection().getNumRandom());
    assertEquals(3, new RandomSelection("RandomSelection3").getNumRandom());
    assertEquals(1, new RandomSelection("8y47yg3f").getNumRandom());
  }

  @Test
  void testValidCheck() {
    try {
      Piece teamOnePiece = board.getTile(Coordinate.of(9, 0)).getPiece().get();
      Piece teamTwoPiece = board.getTile(Coordinate.of(9, 1)).getPiece().get();
      Set<ChessTile> oneMoves = new HashSet<>(board.getMoves(teamOnePiece));
      assertEquals(1, oneMoves.size());
      board.getMoves(teamTwoPiece);
      assertEquals(oneMoves, board.getMoves(teamOnePiece));

      board = BoardSetup.createLocalBoard(RANDOM_SELECTION_FILE_TWO);
      teamOnePiece = board.getTile(Coordinate.of(9, 0)).getPiece().get();
      assertEquals(3, board.getMoves(teamOnePiece).size());

      board = BoardSetup.createLocalBoard(RANDOM_SELECTION_FILE_THREE);
      teamOnePiece = board.getTile(Coordinate.of(9, 0)).getPiece().get();
      assertEquals(6, board.getMoves(teamOnePiece).size());
    } catch (Exception e) {
      fail();
    }
  }
}