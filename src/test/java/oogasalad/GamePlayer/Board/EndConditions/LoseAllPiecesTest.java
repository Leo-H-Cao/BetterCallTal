package oogasalad.GamePlayer.Board.EndConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.Board.BoardTestUtil;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.ValidStateChecker.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoseAllPiecesTest {
  private BoardTestUtil util;

  private static final Logger LOG = LogManager.getLogger(CheckmateTest.class);

  private static final int TEAM_1 = 1;
  private static final int TEAM_2 = 0;

  LoseAllPieces l = new LoseAllPieces();

  private ChessBoard board;
  private List<Piece> pieces;

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

  }

  @Test
  void testNoGameOver(){
    pieces.addAll(List.of(util.makeKing(7, 0, TEAM_1), util.makeRook(6, 6, TEAM_2),
        util.makeRook(0, 1, TEAM_2)));
    board.setPieces(pieces);
    assert(l.getScores(board).isEmpty());
  }

  @Test
  void testLoseAllPiecesPlayerScores(){
    pieces.addAll(List.of(util.makeRook(7, 0, TEAM_2), util.makeRook(6, 6, TEAM_2),
        util.makeRook(0, 1, TEAM_2)));
    board.setPieces(pieces);
    assert(!l.getScores(board).isEmpty());
    assertEquals(0.0, l.getScores(board).get(TEAM_1));
    assertEquals(1.0, l.getScores(board).get(TEAM_2));

  }



}
