package oogasalad.GamePlayer.Movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.Piece;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.PieceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovementTest {
  private Movement movementOne;
  private Movement movementTwo;
  private Movement movementThree;

  private Movement captureOne;
  private Movement captureTwo;

  private ChessBoard board;
  private TurnCriteria turnCriteria;

  private Player playerOne;
  private Player playerTwo;
  private Player playerThree;
  private Player[] players;

  private Piece pieceOne;
  private Piece pieceTwo;
  private Piece pieceThree;
  private Piece pieceFour;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, new int[]{1, 2});
    playerTwo = new Player(1, new int[]{2});
    playerThree = new Player(2, new int[]{});
    players = new Player[]{playerOne, playerTwo, playerThree};

    turnCriteria = new Linear(players);

    board = new ChessBoard(3, 3, turnCriteria, players, List.of());

    movementOne = new Movement(new Coordinate(1, 1), false);
    movementTwo = new Movement(new Coordinate(0, 1), true);
    movementThree = new Movement(new Coordinate(0, -1), false);

    captureOne = new Movement(new Coordinate(1, 0), false);
    captureTwo = new Movement(new Coordinate(1, 1), true);

    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(movementOne), List.of(captureOne, captureTwo), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(movementTwo), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceThree = new Piece(new PieceData(new Coordinate(2, 1), "test3", 0, 2, false,
        List.of(movementOne, movementThree), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceFour = new Piece(new PieceData(new Coordinate(2, 2), "test4", 0, 2, false,
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieces = List.of(pieceOne, pieceTwo, pieceThree, pieceFour);

    board.setPieces(pieces);
  }

  @Test
  void finiteMovementTestHappy() {
    try {
      assertEquals(movementOne.getMoves(pieceOne, board),
          Set.of(board.getTile(new Coordinate(1, 1))));
//      assertEquals(movementOne.movePiece(pieceOne, new Coordinate(1, 1), board), Set.of(board.getTile(new Coordinate(0, 0)), board.getTile(new Coordinate(1, 1))));
      assertEquals(movementThree.getMoves(pieceThree, board),
          Set.of(board.getTile(new Coordinate(2, 0))));
//      assertEquals(movementOne.movePiece(pieceThree, new Coordinate(2, 0), board), Set.of(board.getTile(new Coordinate(2, 0)), board.getTile(new Coordinate(2, 1))));
    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void infiniteMovementTestHappy() {
    try {
      assertEquals(movementTwo.getMoves(pieceTwo, board),
          Set.of(board.getTile(new Coordinate(1, 1)), board.getTile(new Coordinate(1, 2))));
//      assertEquals(movementOne.movePiece(pieceTwo, new Coordinate(1, 2), board), Set.of(board.getTile(new Coordinate(1, 0)), board.getTile(new Coordinate(1, 2))));
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  void finiteCaptureTestHappy() {
    try {
      assertEquals(Set.of(board.getTile(new Coordinate(1, 0))), captureOne.getCaptures(pieceOne, board));
      assertEquals(captureOne.getCaptures(pieceTwo, board), Set.of());
    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void infiniteCaptureTestHappy() {
    try {
      assertEquals(captureTwo.getCaptures(pieceTwo, board), Set.of(board.getTile(new Coordinate(2, 1))));
      assertEquals(captureTwo.getCaptures(pieceOne, board), Set.of(board.getTile(new Coordinate(2, 2))));
      assertEquals(captureTwo.getCaptures(pieceThree, board), Set.of());
    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void finiteMoveCaptureSad() {
    assertThrows(OutsideOfBoardException.class, () -> movementOne.movePiece(pieceOne, new Coordinate(50, 50),board));
    assertThrows(OutsideOfBoardException.class, () -> captureOne.capturePiece(pieceOne, new Coordinate(50, 50),board));

    assertThrows(
        InvalidMoveException.class, () -> movementOne.movePiece(pieceOne, new Coordinate(2, 0),board));
    assertThrows(InvalidMoveException.class, () -> captureOne.capturePiece(pieceOne, new Coordinate(2, 0),board));
  }

  @Test
  void infiniteMoveCaptureSad() {
    assertThrows(OutsideOfBoardException.class, () -> movementTwo.movePiece(pieceOne, new Coordinate(50, 50),board));
    assertThrows(OutsideOfBoardException.class, () -> captureTwo.capturePiece(pieceOne, new Coordinate(50, 50),board));

    assertThrows(
        InvalidMoveException.class, () -> movementTwo.movePiece(pieceOne, new Coordinate(2, 0),board));
    assertThrows(InvalidMoveException.class, () -> captureTwo.capturePiece(pieceOne, new Coordinate(2, 0),board));
  }
}