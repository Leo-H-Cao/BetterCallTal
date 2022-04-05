package oogasalad.GamePlayer.Movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import oogasalad.Editor.Movement.Coordinate;
import oogasalad.Editor.Movement.Movement;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.AtomicEndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.Piece;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.PieceData;
import oogasalad.Editor.Movement.MovementModifiers.Atomic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovementModifierTest {


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
        List.of(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), List.of(new Atomic()), ""), board);
    blackKing = new Piece(new PieceData(Coordinate.of(1, 0), "test2", 0, 1, true,
        List.of(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), List.of(new Atomic()), ""), board);
    whiteAttacker = new Piece(new PieceData(Coordinate.of(1, 1), "test2", 0, 0, false,
        List.of(), List.of(new Movement(List.of(Coordinate.of(0, -1)), false)), Collections.emptyList(), Collections.emptyList(), List.of(new Atomic()),""), board);
    pieces = List.of(whiteKing, blackKing, whiteAttacker);
    board.setPieces(pieces);
  }

  @Test
  void atomicTestHappy() {
    try {
      assertFalse(board.isGameOver());
      // Uncomment when pieces actually use movement modifiers to modify their movement
//      whiteAttacker.move(board.getTile(Coordinate.of(1, 0)));
//      assertTrue(board.isGameOver());
//      assertEquals(Map.of(0, 0.0, 1, 0.0),board.getScores().get());
    } catch(Exception e) {
      fail();
    }
  }
}