package oogasalad.GamePlayer.Movement.CustomMovements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankLeaverTest {

  public static final String BOARD_TEST_FILES_HEADER = "doc/testing_directory/test_boards/";
  public static final String BANK_TEST_FILE = BOARD_TEST_FILES_HEADER + "TicTacToe.json";
  public static final String BANK_TEST_FILE_TWO = BOARD_TEST_FILES_HEADER + "CrazyhouseModified.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(BANK_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testMoves() {
    try {
      Piece whiteTicTacToe = myBoard.getTile(Coordinate.of(2, 4)).
          getPiece().get();
      assertEquals(generateAllTiles(2, 2, myBoard), whiteTicTacToe.getMoves(myBoard));
      assertThrows(InvalidMoveException.class, () -> new BankLeaver(BANK_TEST_FILE).movePiece(whiteTicTacToe, Coordinate.of(2, 3), myBoard));
      assertEquals(
          Set.of(myBoard.getTile(Coordinate.of(2, 4)), myBoard.getTile(Coordinate.of(0, 0))),
          myBoard.move(whiteTicTacToe, Coordinate.of(0, 0)).updatedSquares());
      assertEquals(Collections.emptyList(), new BankLeaver().getRelativeCoords());

      myBoard = BoardSetup.createLocalBoard(BANK_TEST_FILE_TWO);
      Piece whitePawn = myBoard.getTile(Coordinate.of(4, 4)).getPiece().get();
      Piece blackPawn = myBoard.getTile(Coordinate.of(1, 0)).getPiece().get();
      myBoard.move(whitePawn, Coordinate.of(3, 3));
      myBoard.move(blackPawn, Coordinate.of(2, 0));
      assertFalse(myBoard.getMoves(myBoard.getTile(Coordinate.of(7, 9)).getPiece().get()).contains(myBoard.getTile(Coordinate.of(0, 4))) &&
          myBoard.getMoves(whitePawn).contains(myBoard.getTile(Coordinate.of(7, 4))));
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  private Set<ChessTile> generateAllTiles(int rowMax, int colMax, ChessBoard board) {
    Set<ChessTile> tiles = new HashSet<>();
    IntStream.range(0, rowMax + 1).forEach(r -> IntStream.range(0, colMax + 1).forEach(c -> {
      try {
        tiles.add(board.getTile(Coordinate.of(r, c)));
      } catch (OutsideOfBoardException ignored) {
      }
    }));
    return tiles;
  }

  @Test
  void testConfigFiles() {
    try {
      BankLeaver test = new BankLeaver();
      assertEquals(8, test.getBlockCol());
      test = new BankLeaver("badFile");
      assertEquals(8, test.getBlockCol());
      test = new BankLeaverNoRemove();
      assertEquals(3, test.getBlockCol());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testCaptures() {
    try {
      assertEquals(Collections.emptySet(), new BankLeaver().getCaptures(null, null));
      assertThrows(InvalidMoveException.class,
          () -> new BankLeaver().capturePiece(null, null, null));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void equalsTest() {
    BankLeaver test = new BankLeaver();
    assertEquals(test, test);
    assertNotEquals(new BankLeaver(), null);
    assertEquals(new BankLeaver("badFile"), new BankLeaver());
    assertNotEquals(new BankLeaver("TicTacToeConfig"), new BankLeaver());
  }
}