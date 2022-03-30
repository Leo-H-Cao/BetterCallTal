package oogasalad.GamePlayer.Board;

import java.util.List;
import oogasalad.GamePlayer.Board.TurnCriteria.ConstantIncrease;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnTest {

  private ChessBoard board;
  private TurnCriteria turnCriteria;
  private final Player playerOne = new Player(0, null);
  private final Player playerTwo = new Player(1, null);
  private final Player playerThree = new Player(2, null);
  private final Player[] players = {playerOne, playerTwo, playerThree};
  private final List<Piece> pieces = 
  @BeforeEach
  void setUp() {
    board = new ChessBoard(8, 8, turnCriteria, players, null);
    board.setPieces(pieces);
  }

  @Test
  void testLinearHappy() {
    turnCriteria = new ConstantIncrease(players);
    board.move();
  }
}