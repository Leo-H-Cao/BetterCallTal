package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.Piece;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.ConstantIncrease;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnTest {

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
  }

  private void setBoard() {
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
  void testLinearHappy() {
    turnCriteria = new Linear(players);
    setBoard();

    try {
      assertEquals(board.move(pieceOne, new Coordinate(0, 1)).nextPlayer(), 1);
      assertEquals(board.move(pieceTwo, new Coordinate(1, 1)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 1)).nextPlayer(), 0);
    } catch(Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void testIncrHappy() {
    turnCriteria = new ConstantIncrease(players);
    setBoard();

    try {
      assertEquals(board.move(pieceOne, new Coordinate(0, 1)).nextPlayer(), 1);
      assertEquals(board.move(pieceTwo, new Coordinate(1, 1)).nextPlayer(), 1);
      assertEquals(board.move(pieceTwo, new Coordinate(1, 2)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 1)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 2)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 3)).nextPlayer(), 0);
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  void testLinearSad() {
    turnCriteria = new Linear(players);
    setBoard();
    assertThrows(WrongPlayerException.class, () -> board.move(pieceTwo, new Coordinate(0, 1)));
    try {
      board.move(pieceOne, new Coordinate(0, 1));
    } catch(Exception e) {fail();}

    assertThrows(WrongPlayerException.class, () -> board.move(pieceOne, new Coordinate(1, 1)));
  }

  @Test
  void testIncrSad() {
    turnCriteria = new ConstantIncrease(players);
    setBoard();
    assertThrows(WrongPlayerException.class, () -> board.move(pieceTwo, new Coordinate(0, 1)));

    try{
      board.move(pieceOne, new Coordinate(0, 1));
      board.move(pieceTwo, new Coordinate(1, 1));
    } catch(Exception e) {fail();}

    assertThrows(WrongPlayerException.class, () -> board.move(pieceOne, new Coordinate(0, 2)));
  }
}