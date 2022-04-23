package oogasalad.GamePlayer.GamePiece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class PieceClassTest {

  @Test
  void pieceDataToStringTest() {
    PieceData test = new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "test1.png");
    assertEquals("test1: (0, 0); [[(0, 1)]: false]", test.toString());
  }

  @Test
  void outOfBoundsTest() {
    try {
      ChessBoard board = BoardSetup.createLocalBoard(
          "doc/testing_directory/test_boards/RegularGame.json");
      Piece test = new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
          List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "test1.png"));
      assertThrows(
          OutsideOfBoardException.class, () -> test.updateCoordinates(board.getTile(Coordinate.of(0, 0)), board));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void pieceEqualsTest() {
    assertNotEquals(new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "test1.png"))
    , null);
    assertNotEquals(new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
            List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "test1.png"))
        , "string");
  }

  @Test
  void pieceRelativeCapCoordsTest() {
    Piece piece = new Piece(new PieceData(new Coordinate(100, 100), "test1", 0, 0, false,
        List.of(), List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), "test1.png"));
    assertEquals(List.of(Coordinate.of(0, 1)), piece.getRelativeCapCoords());
  }

}
