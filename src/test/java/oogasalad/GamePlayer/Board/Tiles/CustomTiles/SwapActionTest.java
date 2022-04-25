package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.BoardTestUtil;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.BlackHoleAction;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.Swap;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SwapActionTest {

  static final String SWAP_TEST_FILE = BOARD_TEST_FILES_HEADER + "SwapTest.json";

  private ChessBoard myBoard;

  @BeforeEach
  void setUp() {
    try {
      myBoard = BoardSetup.createLocalBoard(SWAP_TEST_FILE);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void swapTest() {
    try {
      Piece rook = myBoard.getTile(Coordinate.of(7, 0)).getPiece().get();
      Piece pawn = myBoard.getTile(Coordinate.of(6, 0)).getPiece().get();
      myBoard.move(rook, Coordinate.of(7, 3));
      assertEquals(pawn, myBoard.getTile(Coordinate.of(7, 3)).getPiece().get());
      assertEquals(rook, myBoard.getTile(Coordinate.of(6, 0)).getPiece().get());
    } catch (Exception e) {
      fail();
    }
  }
}
