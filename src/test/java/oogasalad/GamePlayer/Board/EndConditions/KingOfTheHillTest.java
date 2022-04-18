package oogasalad.GamePlayer.Board.EndConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class KingOfTheHillTest {
  private BoardTestUtil util;

  private static final Logger LOG = LogManager.getLogger(CheckmateTest.class);

  private static final int TEAM_1 = 1;
  private static final int TEAM_2 = 0;

  oogasalad.GamePlayer.ValidStateChecker.Check Check = new Check();
  Checkmate checkmate = new Checkmate();

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
  void Player1Wins(){
    Piece wking = util.makeKing(4, 4, TEAM_2);
    Piece bking = util.makeKing(1, 1, TEAM_1);

    pieces.addAll(List.of(wking, bking));
    board.setPieces(pieces);

    System.out.println(new KingOfTheHill().getScores(board).get(0));
    System.out.println(new KingOfTheHill().getScores(board).get(1));

    assertTrue(new KingOfTheHill().getScores(board).get(TEAM_1) == 1.0);
  }

  @Test
  void Player2Wins(){

  }

  @Test
  void NoWin(){

  }

}
