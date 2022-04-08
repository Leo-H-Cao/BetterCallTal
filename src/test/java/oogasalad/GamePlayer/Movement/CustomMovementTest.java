package oogasalad.GamePlayer.Movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.Movement.CustomMovements.Castling;
import oogasalad.GamePlayer.Movement.CustomMovements.DoubleFirstMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomMovementTest {

  private ChessBoard board;

  private Player playerOne;
  private Player playerTwo;
  private Player[] players;

  private Piece whiteKing;
  private Piece whiteRookR;
  private Piece whiteRookL;
  private Piece whiteBlockerOne;
  private Piece whiteBlockerTwo;

  private Piece otherKing;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, null);
    playerTwo = new Player(1, null);
    players = new Player[]{playerOne, playerTwo};
    board = new ChessBoard(8, 4, new Linear(players), players, List.of());
  }

  @Test
  void castleTestBlock() {
    whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
    whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
    whiteBlockerOne = new Piece(new PieceData(new Coordinate(0, 2), "Pawn", 0, 0, false,
        List.of(new Castling(), new DoubleFirstMove()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
    whiteBlockerTwo = new Piece(new PieceData(new Coordinate(0, 6), "Pawn", 0, 0, false,
        List.of(new Castling(), new DoubleFirstMove()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
    board.setPieces(List.of(whiteKing, whiteRookL, whiteBlockerOne, whiteBlockerTwo));
    assertEquals(Collections.emptySet(), whiteKing.getMoves(board));
  }

  @Test
  void doubleFMTest() {
    try {
      whiteBlockerOne = new Piece(new PieceData(new Coordinate(0, 2), "Pawn", 0, 0, false,
          List.of(new Movement(List.of(new Coordinate(1, 0)), false), new Castling(), new DoubleFirstMove()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
      whiteBlockerTwo = new Piece(new PieceData(new Coordinate(0, 6), "Pawn", 0, 1, false,
          List.of(new Movement(List.of(new Coordinate(1, 0)), false), new Castling(), new DoubleFirstMove()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
      board.setPieces(List.of(whiteBlockerOne, whiteBlockerTwo));
      assertEquals(Set.of(board.getTile(Coordinate.of(1, 2)), board.getTile(Coordinate.of(2, 2))), whiteBlockerOne.getMoves(board));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 2)), board.getTile(Coordinate.of(2,2))), board.move(whiteBlockerOne, Coordinate.of(2,2)).updatedSquares());

      board.move(whiteBlockerTwo, Coordinate.of(1, 6));
      assertEquals(Set.of(board.getTile(Coordinate.of(2, 6))), whiteBlockerTwo.getMoves(board));

      assertEquals(Set.of(), new DoubleFirstMove().getCaptures(whiteBlockerTwo, board));
      assertEquals(List.of(), new DoubleFirstMove().getRelativeCoords());

      assertThrows(InvalidMoveException.class, () -> new DoubleFirstMove().capturePiece(whiteBlockerTwo, null, board));
    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void castleTestNotMainPiece() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, false,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, false,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, false,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertEquals(Collections.emptySet(), whiteKing.getMoves(board));
    } catch (Exception e) {
      fail();
    }
  }

  // Uncomment once movement fully implemented
  @Test
  void castleTestMovementK() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Movement(Coordinate.of(0, 1), false), new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookL));
      whiteKing.move(board.getTile(Coordinate.of(0, 5)), board);
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 6))), whiteKing.getMoves(board));
    } catch (Exception e) {
      fail();
    }
  }

  // Uncomment once movement fully implemented
  @Test
  void castleTestMovementR() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 1), "whiteRookL", 0, 0, true,
          List.of(new Movement(Coordinate.of(0, -1), false)), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookL));
      whiteRookL.move(board.getTile(Coordinate.of(0, 0)), board);
      assertEquals(Collections.emptySet(), whiteKing.getMoves(board));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void castleTestKCastle() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookR));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 6))), whiteKing.getMoves(board));

      whiteKing.move(board.getTile(Coordinate.of(0, 6)), board);

      assertEquals(List.of(), board.getTile(Coordinate.of(0, 4)).getPieces());
      assertEquals(List.of(), board.getTile(Coordinate.of(0, 7)).getPieces());
      assertEquals(whiteKing, board.getTile(Coordinate.of(0, 6)).getPiece().get());
      assertEquals(whiteRookR, board.getTile(Coordinate.of(0, 5)).getPiece().get());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void castleTestQCastle() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookL));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 2))), whiteKing.getMoves(board));

      whiteKing.move(board.getTile(Coordinate.of(0, 2)), board);

      assertEquals(whiteKing, board.getTile(Coordinate.of(0, 2)).getPiece().get());
      assertEquals(whiteRookL, board.getTile(Coordinate.of(0, 3)).getPiece().get());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void castleTestKQCastle() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 6)), board.getTile(Coordinate.of(0, 2))), whiteKing.getMoves(board));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void castleTestOther() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), ""));
      board.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));

      assertEquals(List.of(), new Castling().getRelativeCoords());
      assertThrows(InvalidMoveException.class, () -> new Castling().capturePiece(null, null, null));
    } catch (Exception e) {
      fail();
    }
  }
}