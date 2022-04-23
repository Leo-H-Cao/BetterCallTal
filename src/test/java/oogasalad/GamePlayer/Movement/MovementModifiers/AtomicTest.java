package oogasalad.GamePlayer.Movement.MovementModifiers;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.AtomicEndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifiers.Atomic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtomicTest {


  private ChessBoard board;

  private Player playerOne;
  private Player playerTwo;
  private Player[] players;

  private Piece whiteKing;
  private Piece blackKing;
  private Piece whiteAttacker;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, new int[]{1});
    playerTwo = new Player(1, new int[]{0});
    players = new Player[]{playerOne, playerTwo};

    board = new ChessBoard(8, 8,  new Linear(players), players, List.of(new AtomicEndCondition()));
    whiteKing = new Piece(new PieceData(Coordinate.of(0, 0), "test1", 0, 0, true,
        List.of(), Collections.emptyList(), Collections.emptyList(), List.of(new Atomic()), ""));
    blackKing = new Piece(new PieceData(Coordinate.of(1, 0), "test2", 0, 1, true,
        List.of(), Collections.emptyList(), Collections.emptyList(), List.of(new Atomic()), ""));
    whiteAttacker = new Piece(new PieceData(Coordinate.of(1, 1), "test2", 0, 0, false,
        List.of(), List.of(new Movement(List.of(Coordinate.of(0, -1)), false)), Collections.emptyList(), List.of(new Atomic()),""));
    pieces = List.of(whiteKing, blackKing, whiteAttacker);
    board.setPieces(pieces);
  }

  @Test
  void atomicTest() {
    try {
      assertFalse(board.isGameOver());
      whiteAttacker.move(board.getTile(Coordinate.of(1, 0)), board);
      assertTrue(board.isGameOver());
      assertEquals(Map.of(0, 0.0, 1, 0.0), board.getScores());
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  void testBadConfigFiles() {
      Atomic test = new Atomic("jisufd5878");
      assertEquals(List.of("Pawn"), test.getExplosionImmuneNames());
  }
}