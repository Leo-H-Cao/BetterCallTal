package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeleportTest {
  static final String TELEPORT_TEST_FILE = BOARD_TEST_FILES_HEADER + "TeleportTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(TELEPORT_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void teleportTest() {
    try {
      Piece rook = myBoard.getTile(Coordinate.of(7, 0)).getPiece().get();
      Piece rookTwo = myBoard.getTile(Coordinate.of(7, 7)).getPiece().get();
      myBoard.move(rook, Coordinate.of(7 ,3));
      assertEquals(Coordinate.of(0, 0), rook.getCoordinates());
      myBoard.move(rookTwo, Coordinate.of(7 ,3));
      assertEquals(Coordinate.of(7, 3), rookTwo.getCoordinates());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void badConfigFileTest() {
    Teleport test = new Teleport("bad file");
    assertEquals(Coordinate.of(0, 0), test.getTeleportLocation());
  }
}