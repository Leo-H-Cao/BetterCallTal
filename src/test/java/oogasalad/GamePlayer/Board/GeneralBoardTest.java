package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneralBoardTest {

  private ChessBoard board;
  private TurnCriteria turnCriteria;

  private Player playerOne;
  private Player playerTwo;
  private Player playerThree;
  private Player[] players;

  private Piece pieceOne;
  private Piece pieceTwo;
  private Piece pieceThree;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, null);
    playerTwo = new Player(1, null);
    playerThree = new Player(2, null);
    players = new Player[]{playerOne, playerTwo, playerThree};

    turnCriteria = new Linear(players);

    board = new ChessBoard(8, 8, turnCriteria, players, List.of());
    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceThree = new Piece(new PieceData(new Coordinate(2, 0), "test3", 0, 2, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieces = List.of(pieceOne, pieceTwo, pieceThree);
    board.setPieces(pieces);
  }

  @Test
  void generalBoardTestHappy() {
    try {
      assertFalse(board.setPieces(null));
//      assertEquals(Set.of(board.getTile(new Coordinate(0, 1))), board.getMoves(pieceOne));
      assertTrue(board.getTile(new Coordinate(0, 0)).removePiece(pieceOne));
      assertFalse(board.getTile(new Coordinate(0, 0)).removePiece(pieceOne));
      assertEquals("(0, 0): []", board.getTile(new Coordinate(0, 0)).toString());
    } catch(Exception e) {
      fail();
    }
  }
}