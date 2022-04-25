package oogasalad.GamePlayer.GamePiece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.Test;

public class PieceClassTest {

  @Test
  void pieceDataToStringTest() {
    PieceData test = new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), "test1.png");
    assertEquals("test1: (0, 0); [[(0, 1)]: false]", test.toString());
  }

  @Test
  void outOfBoundsTest() {
    try {
      ChessBoard board = BoardSetup.createLocalBoard(
          "doc/testing_directory/test_boards/RegularGame.json");
      Piece test = new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
          List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), "test1.png"));
      assertThrows(
          OutsideOfBoardException.class,
          () -> test.updateCoordinates(board.getTile(Coordinate.of(0, 0)), board));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void pieceEqualsTest() {
    assertNotEquals(new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
            List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
            Collections.emptyList(), Collections.emptyList(), "test1.png"))
        , null);
    assertNotEquals(new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
            List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
            Collections.emptyList(), Collections.emptyList(), "test1.png"))
        , "string");
  }

  @Test
  void pieceRelativeCapCoordsTest() {
    Piece piece = new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
        List.of(), List.of(new Movement(List.of(new Coordinate(0, 1)), false)),
        Collections.emptyList(), Collections.emptyList(), "test1.png"));
    assertEquals(List.of(Coordinate.of(0, 1)), piece.getRelativeCapCoords());
  }

  @Test
  void defaultConstructorTest() {
   Piece piece = new Piece();
   assertEquals("default", piece.getName());
    assertEquals("default", piece.getImgFile());
    assertEquals(Coordinate.of(0, 0), piece.getCoordinates());
    assertEquals(Collections.emptySet(), piece.getMoves());
    assertEquals(Collections.emptySet(), piece.getCaptures());
    assertEquals(0, piece.getTeam());
    assertEquals(0.0, piece.getPieceValue());
    assertFalse(piece.isTargetPiece());
  }

  @Test
  void defaultMovementHandlerTest() {
    MovementHandler test = new MovementHandler();
    assertTrue(test.getMovements().isEmpty());
    assertTrue(test.getCaptures().isEmpty());
    assertTrue(test.getMovementModifiers().isEmpty());
  }
}
