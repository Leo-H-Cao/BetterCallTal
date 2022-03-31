package oogasalad.GamePlayer.Movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovementTest {
  private Movement movementOne;
  private Movement movementTwo;
  private Movement movementThree;

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
    playerOne = new Player(0, new int[]{1});
    playerTwo = new Player(1, new int[]{0});
    playerThree = new Player(2, new int[]{});
    players = new Player[]{playerOne, playerTwo, playerThree};

    movementOne = new Movement(new Coordinate(1, 1), false);
    movementTwo = new Movement(new Coordinate(0, 1), true);
    movementThree = new Movement(new Coordinate(0, -1), false);

    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(movementOne), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(movementTwo), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceThree = new Piece(new PieceData(new Coordinate(2, 1), "test3", 0, 2, false,
        List.of(movementOne, movementThree), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieces = List.of(pieceOne, pieceTwo, pieceThree);

    turnCriteria = new Linear(players);

    board = new ChessBoard(3, 3, turnCriteria, players, List.of());
    board.setPieces(pieces);
  }

  @Test
  void finiteMovementTestHappy() {
    try {
      assertEquals(movementOne.getMoves(pieceOne, board),
          Set.of(board.getTile(new Coordinate(1, 1))));
      assertEquals(movementThree.getMoves(pieceThree, board),
          Set.of(board.getTile(new Coordinate(2, 0))));
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
    } catch(Exception e) {
      fail();
    }
  }
}