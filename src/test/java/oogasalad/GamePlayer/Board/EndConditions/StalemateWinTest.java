package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Movement.CustomMovements.CheckersCaptureTest.CHECKERS_TEST_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.BoardTestUtil;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.ValidStateChecker.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class StalemateWinTest {
  private BoardTestUtil util;

  private static final Logger LOG = LogManager.getLogger(CheckmateTest.class);

  private static final int TEAM_1 = 1;
  private static final int TEAM_2 = 0;

  oogasalad.GamePlayer.ValidStateChecker.Check Check = new Check();
  Checkmate checkmate = new Checkmate();

  private ChessBoard board;
  private List<Piece> pieces;

  StalemateWin sw = new StalemateWin();

  @BeforeEach
  void setUp() {

    util = new BoardTestUtil();

    Player playerOne = new Player(TEAM_1, new int[]{TEAM_2});
    Player playerTwo = new Player(TEAM_2, new int[]{TEAM_1});
    Player[] players = new Player[]{playerOne, playerTwo};

    Linear turnCriteria = new Linear(players);
    TwoMoves endCondition = new TwoMoves();

    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));
    pieces = new ArrayList<>();
    pieces.addAll(List.of(util.makeKing(7, 0, TEAM_1),
        util.makeRook(6, 6, TEAM_2),
        util.makeRook(0, 1, TEAM_2)));

    board.setPieces(pieces);
  }

  @Test
  void reachedStalemate() throws EngineException {
    assertTrue(sw.isStalemate(board));
  }

  @Test
  void notStalemateTest() {
    try {
      board = BoardSetup.createLocalBoard(CHECKERS_TEST_FILE);
      assertEquals(Collections.emptyMap(), sw.getScores(board));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void testStalemateScores(){
    assertEquals(0.0, sw.getScores(board).get(TEAM_1));
    assertEquals(1.0, sw.getScores(board).get(TEAM_2));
  }

}