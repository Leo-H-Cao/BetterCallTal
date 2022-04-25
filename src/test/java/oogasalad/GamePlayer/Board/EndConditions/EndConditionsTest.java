package oogasalad.GamePlayer.Board.EndConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.EndConditions.NoEndCondition;
import oogasalad.GamePlayer.Board.EndConditions.PawnReachesEnd;
import oogasalad.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.MoveAfterGameEndException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndConditionsTest {

  private ChessBoard board;
  private TurnCriteria turnCriteria;
  private EndCondition endCondition;

  private Player playerOne;
  private Player playerTwo;
  private Player[] players;

  private Piece pieceOne;
  private Piece pieceTwo;
  private List<Piece> pieces;

  private Map<Integer, Double> scores;

  @BeforeEach
  void setUp() {
    scores = Map.of(0, 0.5, 1, 0.5);

    playerOne = new Player(0, null);
    playerTwo = new Player(1, null);
    players = new Player[]{playerOne, playerTwo};

    turnCriteria = new Linear(players);
  }

  private void setBoard() {
    board = new ChessBoard(8, 3, turnCriteria, players, List.of(endCondition));

    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "Pawn", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "Pawn", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
    pieces = List.of(pieceOne, pieceTwo);

    board.setPieces(pieces);
  }

  @Test
  void testTwoMovesHappy() {
    endCondition = new TwoMoves();
    setBoard();

    try {
      board.move(pieceOne, new Coordinate(0, 1));
      assertFalse(board.isGameOver());
      board.move(pieceTwo, new Coordinate(1, 1));
      assertFalse(board.isGameOver());
      board.move(pieceOne, new Coordinate(0, 2));
      assertFalse(board.isGameOver());
      board.move(pieceTwo, new Coordinate(1, 2));
      assertTrue(board.isGameOver());
      assertEquals(board.getScores(), scores);
    } catch(Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void testTwoMovesSad() {
    endCondition = new TwoMoves();
    setBoard();
    try {
      board.move(pieceOne, new Coordinate(0, 1));
      board.move(pieceTwo, new Coordinate(1, 1));
      board.move(pieceOne, new Coordinate(0, 2));
      board.move(pieceTwo, new Coordinate(1, 2));
      assertThrows(MoveAfterGameEndException.class, () -> board.move(pieceOne, new Coordinate(0, 3)));
    } catch(Throwable e) {
      fail();
    }
  }

  @Test
  void testPawnReachesEnd() {
    endCondition = new PawnReachesEnd();
    setBoard();
    try {
      assertTrue(board.isGameOver());
      assertEquals(board.getScores(), Map.of(0, 1.0, 1, 0.0));
      assertEquals(Collections.emptySet(), board.getMoves(pieceOne));
      assertThrows(MoveAfterGameEndException.class, () -> board.move(pieceTwo, new Coordinate(1, 2)));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void testNoEndCondition() {
    endCondition = new NoEndCondition();
    setBoard();
    try {
      assertFalse(board.isGameOver());
      assertEquals(Map.of(), board.getScores());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void drawConfigBadFileTest() {
    KnownDraws test = new KnownDraws("KnownDrawsMalformed");
    assertEquals(Collections.emptyList(), test.getDrawConfigs());
  }
}