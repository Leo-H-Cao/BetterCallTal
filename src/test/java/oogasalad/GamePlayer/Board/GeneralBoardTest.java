package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
<<<<<<< HEAD
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Player;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.Frontend.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;
import oogasalad.Frontend.GamePlayer.GamePiece.PieceData;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;
import oogasalad.Frontend.GamePlayer.Movement.Movement;
=======
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.Editor.Movement.Coordinate;
import oogasalad.Editor.Movement.Movement;
>>>>>>> 13bbc0787cf1e68b3a5d1149146123c54d282f81
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

    board = new ChessBoard(3, 3, turnCriteria, players, List.of());
    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "test1.png"), board);
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
      assertEquals(3, board.getBoardLength());
      assertEquals(3, board.getBoardHeight());
      assertFalse(board.setPieces(null));
      assertEquals(Set.of(board.getTile(new Coordinate(0, 1))), board.getMoves(pieceOne));
      assertTrue(board.getTile(new Coordinate(0, 0)).removePiece(pieceOne));
      assertFalse(board.getTile(new Coordinate(0, 0)).removePiece(pieceOne));
      board.placePiece(new Coordinate(0, 0), pieceOne);
      assertEquals(board.getTile(new Coordinate(0, 0)).getPiece().get(), pieceOne);
      board.getTile(new Coordinate(0, 0)).removePiece(pieceOne);
      assertEquals("(0, 0): []", board.getTile(new Coordinate(0, 0)).toString());
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  void boardIteratorTestHS() {
    try {
      Iterator<ChessTile> iterator = board.iterator();
      for(int i=0; i<3; i++) {
        for(int j=0; j<3; j++) {
          assertTrue(iterator.hasNext());
          assertEquals(iterator.next(), board.getTile(new Coordinate(i, j)));
        }
      }
      assertFalse(iterator.hasNext());

      AtomicInteger count = new AtomicInteger();
      board.forEach((c) -> {
        count.getAndIncrement();});
      assertEquals(9, count.get());
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  void testPieceGeneralHS() {
    try {
      assertEquals("test1", pieceOne.getName());
      assertEquals("test1.png", pieceOne.getImgFile());
    } catch(Exception e) {
      fail();
    }
  }
}