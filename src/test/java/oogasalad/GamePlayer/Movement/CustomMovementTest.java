package oogasalad.GamePlayer.Movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.CustomMovements.Castling;
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
    board = new ChessBoard(8, 2, new Linear(players), players, List.of());
  }

  @Test
  void castleTestBlock() {
    whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    whiteBlockerOne = new Piece(new PieceData(new Coordinate(0, 2), "whiteBlockerOne", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    whiteBlockerTwo = new Piece(new PieceData(new Coordinate(0, 6), "whiteBlockerTwo", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    board.setPieces(List.of(whiteKing, whiteRookL, whiteBlockerOne, whiteBlockerTwo));
    assertEquals(Collections.emptySet(), whiteKing.getMoves());
  }

  @Test
  void castleTestNotMainPiece() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, false,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, false,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, false,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertEquals(Collections.emptySet(), whiteKing.getMoves());
    } catch (Exception e) {
      fail();
    }
  }

  // Uncomment once movement fully implemented
  //@Test
  void castleTestMovementK() {
    try {
      otherKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Movement(Coordinate.of(0, 1), false), new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookL));
      whiteKing.move(board.getTile(Coordinate.of(0, 5)));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 6))), whiteKing.getMoves());
    } catch (Exception e) {
      fail();
    }
  }

  // Uncomment once movement fully implemented
  //@Test
  void castleTestMovementR() {
    try {
      otherKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 1), "whiteRookL", 0, 0, true,
          List.of(new Movement(Coordinate.of(0, -1), false)), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookL));
      whiteRookL.move(board.getTile(Coordinate.of(0, 0)));
      assertEquals(Collections.emptySet(), whiteKing.getMoves());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void castleTestKCastle() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookR));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 6))), whiteKing.getMoves());

      //uncomment when move fully implemented
//      whiteKing.move(board.getTile(Coordinate.of(0, 6)));
//      assertEquals(whiteKing, board.getTile(Coordinate.of(0, 6)).getPiece().get());
//      assertEquals(whiteRookR, board.getTile(Coordinate.of(0, 5)).getPiece().get());
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
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookL));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 2))), whiteKing.getMoves());
//      whiteKing.move(board.getTile(Coordinate.of(0, 2)));
//      assertEquals(whiteKing, board.getTile(Coordinate.of(0, 2)).getPiece().get());
//      assertEquals(whiteRookR, board.getTile(Coordinate.of(0, 3)).getPiece().get());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void castleTestKQCastle() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertEquals(Set.of(board.getTile(Coordinate.of(0, 6)), board.getTile(Coordinate.of(0, 2))), whiteKing.getMoves());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void castleTestOther() {
    try {
      whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      whiteRookL = new Piece(new PieceData(new Coordinate(0, 0), "whiteRookL", 0, 0, true,
          List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), ""), board);
      board.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertThrows(InvalidMoveException.class, () -> new Castling().capturePiece(null, null, null));
    } catch (Exception e) {
      fail();
    }
  }
}