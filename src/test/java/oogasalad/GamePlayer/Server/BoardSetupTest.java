package oogasalad.GamePlayer.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Board.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardSetupTest {
  ChessBoard myBoard;
  static final String PATH_HEADER = "doc/testing_directory/test_boards/";
  static final String JSON_FILE_PATH = PATH_HEADER + "RegularGame.json";
  static final String OUT_OF_BOUNDS_FILE = PATH_HEADER + "/malformed_game_files/OutOfBoundTileAndPiece.json";
  static final String MALFORMED_CLASS_NAMES_FILE = PATH_HEADER + "/malformed_game_files/MalformedClassNames.json";
  static final String NO_TILE_SECTION_FILE = PATH_HEADER + "/malformed_game_files/NoTileSection.json";
  static final String NO_TILE_IMG_FILE = PATH_HEADER + "/malformed_game_files/NoCustomTileImage.json";

  @BeforeEach
  void setup () throws IOException {
    myBoard = BoardSetup.createLocalBoard(JSON_FILE_PATH);
  }


  @Test
  void testBoardSize () {
    assertEquals(8, myBoard.getBoardLength());
    assertEquals(8, myBoard.getBoardHeight());
  }


  @Test
  void testPiecePlacement() throws OutsideOfBoardException {
    ChessTile pawnSquare = myBoard.getTile(new Coordinate(1, 0));
    assertEquals("Pawn", pawnSquare.getPiece().get().getName());
  }

  @Test
  void testGetPlayers(){
    assertEquals(0, myBoard.getPlayers()[0].teamID());
    assertEquals(1, myBoard.getPlayers()[1].teamID());
  }

  @Test
  void testOutOfBounds() {
    try {
      myBoard = BoardSetup.createLocalBoard(OUT_OF_BOUNDS_FILE);
      assertNotNull(myBoard);
      assertTrue(myBoard.getTile(Coordinate.of(0, 0)).getPieces().isEmpty());
      assertTrue(myBoard.getTile(Coordinate.of(0, 0)).getCustomImg().isEmpty());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void testMalformedReflection() {
    assertThrows(IOException.class, () -> BoardSetup.createLocalBoard(MALFORMED_CLASS_NAMES_FILE));
  }

  @Test
  void testNoTileSectionInJSON() {
    try {
      myBoard = BoardSetup.createLocalBoard(NO_TILE_SECTION_FILE);
      assertNotNull(myBoard);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testNoTileImage() {
    try {
      myBoard = BoardSetup.createLocalBoard(NO_TILE_IMG_FILE);
      assertNotNull(myBoard);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
