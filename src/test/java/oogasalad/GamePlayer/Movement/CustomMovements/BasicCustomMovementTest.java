package oogasalad.GamePlayer.Movement.CustomMovements;

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
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasicCustomMovementTest {

  private ChessBoard myBoard;

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
    myBoard = new ChessBoard(8, 4, new Linear(players), players, List.of());
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
    myBoard.setPieces(List.of(whiteKing, whiteRookL, whiteBlockerOne, whiteBlockerTwo));
    assertEquals(Collections.emptySet(), whiteKing.getMoves(myBoard));
  }

  @Test
  void doubleFMTest() {
    try {
      whiteBlockerOne = new Piece(new PieceData(new Coordinate(0, 2), "Pawn", 0, 0, false,
          List.of(new Movement(List.of(new Coordinate(1, 0)), false), new Castling(), new DoubleFirstMove()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
      whiteBlockerTwo = new Piece(new PieceData(new Coordinate(0, 6), "Pawn", 0, 1, false,
          List.of(new Movement(List.of(new Coordinate(1, 0)), false), new Castling(), new DoubleFirstMove()), Collections.emptyList(), List.of(), Collections.emptyList(), ""));
      myBoard.setPieces(List.of(whiteBlockerOne, whiteBlockerTwo));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(1, 2)), myBoard.getTile(Coordinate.of(2, 2))), whiteBlockerOne.getMoves(
          myBoard));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(0, 2)), myBoard.getTile(Coordinate.of(2,2))), myBoard.move(whiteBlockerOne, Coordinate.of(2,2)).updatedSquares());

      myBoard.move(whiteBlockerTwo, Coordinate.of(1, 6));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(2, 6))), whiteBlockerTwo.getMoves(myBoard));

      assertEquals(Set.of(), new DoubleFirstMove().getCaptures(whiteBlockerTwo, myBoard));
      assertEquals(List.of(), new DoubleFirstMove().getRelativeCoords());


      assertThrows(InvalidMoveException.class, () -> new DoubleFirstMove().capturePiece(whiteBlockerTwo, null,
          myBoard));
      assertThrows(InvalidMoveException.class, () -> new DoubleFirstMove().movePiece(whiteBlockerTwo, Coordinate.of(0, 2),
          myBoard));

      myBoard = BoardSetup.createLocalBoard("doc/testing_directory/test_boards/DoubleFirstMoveBlocked.json");
      assertEquals(Set.of(),
          myBoard.getMoves(myBoard.getTile(Coordinate.of(0, 0)).getPiece().get()));
    } catch(Exception e) {
      fail();
    } catch (Throwable e) {
      e.printStackTrace();
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
      myBoard.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertEquals(Collections.emptySet(), whiteKing.getMoves(myBoard));
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
      myBoard.setPieces(List.of(whiteKing, whiteRookL));
      whiteKing.move(myBoard.getTile(Coordinate.of(0, 5)), myBoard);
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(0, 6))), whiteKing.getMoves(myBoard));
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
      myBoard.setPieces(List.of(whiteKing, whiteRookL));
      whiteRookL.move(myBoard.getTile(Coordinate.of(0, 0)), myBoard);
      assertEquals(Collections.emptySet(), whiteKing.getMoves(myBoard));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void castleTestSad() {
    whiteKing = new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
        Collections.emptyList(), ""));
    whiteRookR = new Piece(new PieceData(new Coordinate(0, 7), "whiteRookR", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(),
        Collections.emptyList(), ""));
    assertEquals(Set.of(), new Castling().getCaptures(null, null));
    assertThrows(InvalidMoveException.class, () -> new Castling().movePiece(whiteKing, Coordinate.of(0, 2),
        myBoard));
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
      myBoard.setPieces(List.of(whiteKing, whiteRookR));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(0, 6))), whiteKing.getMoves(myBoard));

      whiteKing.move(myBoard.getTile(Coordinate.of(0, 6)), myBoard);

      assertEquals(List.of(), myBoard.getTile(Coordinate.of(0, 4)).getPieces());
      assertEquals(List.of(), myBoard.getTile(Coordinate.of(0, 7)).getPieces());
      assertEquals(whiteKing, myBoard.getTile(Coordinate.of(0, 6)).getPiece().get());
      assertEquals(whiteRookR, myBoard.getTile(Coordinate.of(0, 5)).getPiece().get());
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
      myBoard.setPieces(List.of(whiteKing, whiteRookL));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(0, 2))), whiteKing.getMoves(myBoard));

      whiteKing.move(myBoard.getTile(Coordinate.of(0, 2)), myBoard);

      assertEquals(whiteKing, myBoard.getTile(Coordinate.of(0, 2)).getPiece().get());
      assertEquals(whiteRookL, myBoard.getTile(Coordinate.of(0, 3)).getPiece().get());
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
      myBoard.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));
      assertEquals(Set.of(myBoard.getTile(Coordinate.of(0, 6)), myBoard.getTile(Coordinate.of(0, 2))), whiteKing.getMoves(
          myBoard));
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
      myBoard.setPieces(List.of(whiteKing, whiteRookR, whiteRookL));

      assertEquals(List.of(), new Castling().getRelativeCoords());
      assertThrows(InvalidMoveException.class, () -> new Castling().capturePiece(null, null, null));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void enPassantTestHappy() {
    try {
      ChessBoard chessBoard = BoardSetup.createLocalBoard(
          "doc/games/PresentationBoardUpdated.json");
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

    } catch(Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void enPassantTestSad() {
    try {
      assertThrows(InvalidMoveException.class, () -> new EnPassant().movePiece(null, null, null));
      assertEquals(Collections.emptySet(), new EnPassant().getMoves(null, null));
      ChessBoard chessBoard = BoardSetup.createLocalBoard(
          "doc/games/PresentationBoardUpdated.json");
      Piece whiteReference = chessBoard.getTile(Coordinate.of(6, 6)).getPiece().get();
      chessBoard.move(chessBoard.getTile(Coordinate.of(6, 6)).getPiece().get(), Coordinate.of(4, 6));
      assertThrows(InvalidMoveException.class, () -> new EnPassant().capturePiece(whiteReference, Coordinate.of(2, 2), chessBoard));
    } catch(Throwable e) {
      e.printStackTrace();
      fail();
    }
  }
}