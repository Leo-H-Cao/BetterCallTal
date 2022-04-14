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
import oogasalad.GamePlayer.Movement.CustomMovements.EnPassant;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
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

  @Test
  void enPassantTestHappy() {
    try {
      BoardSetup setup = new BoardSetup("doc/GameEngineResources/PresentationBoardUpdated.json");
      ChessBoard chessBoard = setup.createBoard();
      Piece whiteReference = chessBoard.getTile(Coordinate.of(6, 6)).getPiece().get();
      chessBoard.move(chessBoard.getTile(Coordinate.of(6, 6)).getPiece().get(), Coordinate.of(4, 6));
      chessBoard.move(chessBoard.getTile(Coordinate.of(1, 1)).getPiece().get(), Coordinate.of(3, 1));
      chessBoard.move(chessBoard.getTile(Coordinate.of(4, 6)).getPiece().get(), Coordinate.of(3, 6));
      chessBoard.move(chessBoard.getTile(Coordinate.of(1, 7)).getPiece().get(), Coordinate.of(3, 7));
      chessBoard.move(chessBoard.getTile(Coordinate.of(3, 6)).getPiece().get(), Coordinate.of(2, 7));
      assertEquals(Collections.emptyList(), chessBoard.getTile(Coordinate.of(3, 6)).getPieces());
      assertEquals(Collections.emptyList(), chessBoard.getTile(Coordinate.of(3, 7)).getPieces());
      assertEquals(List.of(whiteReference), chessBoard.getTile(Coordinate.of(2, 7)).getPieces());

      chessBoard.move(chessBoard.getTile(Coordinate.of(3, 1)).getPiece().get(), Coordinate.of(4, 1));
      chessBoard.move(chessBoard.getTile(Coordinate.of(6, 0)).getPiece().get(), Coordinate.of(5, 0));
      chessBoard.move(chessBoard.getTile(Coordinate.of(1, 0)).getPiece().get(), Coordinate.of(2, 0));
      chessBoard.move(chessBoard.getTile(Coordinate.of(5, 0)).getPiece().get(), Coordinate.of(4, 0));
      assertEquals(Set.of(chessBoard.getTile(Coordinate.of(5, 1))), chessBoard.getTile(Coordinate.of(4, 1)).getPiece().get().getMoves(chessBoard));

      Piece blackReference = chessBoard.getTile(Coordinate.of(1, 3)).getPiece().get();
      chessBoard.move(blackReference, Coordinate.of(3, 3));
      chessBoard.move(chessBoard.getTile(Coordinate.of(6, 3)).getPiece().get(), Coordinate.of(5, 3));
      chessBoard.move(blackReference, Coordinate.of(4, 3));
      chessBoard.move(chessBoard.getTile(Coordinate.of(6, 4)).getPiece().get(), Coordinate.of(4, 4));
      chessBoard.move(chessBoard.getTile(Coordinate.of(1, 2)).getPiece().get(), Coordinate.of(2, 2));
      assertEquals(Set.of(), blackReference.getMoves(chessBoard));

    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void enPassantTestSad() {
    try {
      assertThrows(InvalidMoveException.class, () -> new EnPassant().movePiece(null, null, null));
      assertEquals(Collections.emptySet(), new EnPassant().getMoves(null, null));
      BoardSetup setup = new BoardSetup("doc/GameEngineResources/PresentationBoardUpdated.json");
      ChessBoard chessBoard = setup.createBoard();
      Piece whiteReference = chessBoard.getTile(Coordinate.of(6, 6)).getPiece().get();
      chessBoard.move(chessBoard.getTile(Coordinate.of(6, 6)).getPiece().get(), Coordinate.of(4, 6));
      assertThrows(InvalidMoveException.class, () -> new EnPassant().capturePiece(whiteReference, Coordinate.of(2, 2), chessBoard));
    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}