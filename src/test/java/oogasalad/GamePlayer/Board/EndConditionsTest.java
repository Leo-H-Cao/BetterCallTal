package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.MoveAfterGameEndException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndConditionsTest {

  private ChessBoard board;
  private TurnCriteria turnCriteria;
  private EndCondition endCondition;

  private Player playerOne;
  private Player playerTwo;
  private Player[] players;

  private Piece pieceOne;
  private Piece pieceTwo;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, null);
    playerTwo = new Player(1, null);
    players = new Player[]{playerOne, playerTwo};

    turnCriteria = new Linear(players);
  }

  private void setBoard() {
    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));

    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieces = List.of(pieceOne, pieceTwo);

    board.setPieces(pieces);
  }

  @Test
  void testTwoMovesHappy() {
    endCondition = new TwoMoves();
    setBoard();

    try {
      board.move(pieceOne, new Coordinate(0, 1));
      assertFalse(board.isGameOver());
      board.move(pieceTwo, new Coordinate(1, 1));
      assertFalse(board.isGameOver());
      board.move(pieceOne, new Coordinate(0, 2));
      assertFalse(board.isGameOver());
      board.move(pieceTwo, new Coordinate(1, 2));
      assertTrue(board.isGameOver());
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  void testTwoMovesSad() {
    endCondition = new TwoMoves();
    setBoard();
    try {
      board.move(pieceOne, new Coordinate(0, 1));
      board.move(pieceTwo, new Coordinate(1, 1));
      board.move(pieceOne, new Coordinate(0, 2));
      board.move(pieceTwo, new Coordinate(1, 2));
      assertThrows(MoveAfterGameEndException.class, () -> board.move(pieceOne, new Coordinate(0, 3)));
    } catch(Exception e) {
      fail();
    }
  }
}