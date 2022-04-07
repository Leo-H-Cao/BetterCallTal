package oogasalad.backend.PieceTests;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class PieceClassTest {

  private ChessBoard board;
  private Piece piece;
  private static final int SIZE = 8;

  @BeforeEach
  void setup() {
    
//    PieceData data = new PieceData();
//    board = new ChessBoard(SIZE, SIZE, );
//    piece = new Piece(data, board);

  }

  /**
   * Checks to see if a piece moves correctly to a valid tile based on a players move
   */
  @Test
  void movedIntoValidTile() {

  }

  /**
   * Checks to see if somehow a person tries to move to an invalid tile, the piece does not respond.
   */
  @Test
  void movedIntoInvalidTile() {

  }
}
