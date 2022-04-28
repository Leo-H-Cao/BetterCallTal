package oogasalad.GamePlayer.Board.EndConditions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.BoardTestUtil;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.ValidStateChecker.Check;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.BasicMovement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CheckmateValidator
 * @author Jose Santillan
 */
public class CheckmateTest {

  private BoardTestUtil util;

  private static final Logger LOG = LogManager.getLogger(CheckmateTest.class);

  private static final int TEAM_1 = 1;
  private static final int TEAM_2 = 0;

  Check check = new Check();
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



  Piece makePawn(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "pawn" + team, 0, team, false,

        List.of(new BasicMovement(List.of(new Coordinate(-1, 0), new Coordinate(1, 0)), false)),
        List.of(new BasicMovement(List.of(new Coordinate(-1, 0), new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), ""));

  }



  /**
   * 2 Rooks pressuring the king (king cannot move nor capture rooks)
   * @throws OutsideOfBoardException
   */
  @Test
  void playerInMate() throws EngineException {

    pieces.addAll(List.of(util.makeKing(0, 0, TEAM_1),
        util.makeRook(2, 0, TEAM_2),
        util.makeRook(2, 1, TEAM_2)));

    board.setPieces(pieces);
    LOG.debug(board);

    assertTrue(!check.isValid(board, TEAM_1));
    assertTrue(checkmate.isInMate(board, TEAM_1));

    assertTrue(checkmate.getScores(board).get(1)==0.0);
  }

  @Test
  void playerSeemsToBeInMateButInStaleMate() throws EngineException {
    pieces.addAll(List.of(util.makeKing(0, 0, TEAM_1),
        makePawn(1, 0, TEAM_2),
        makePawn(2, 1, TEAM_2),
        makePawn(1, 1, TEAM_2),
        makePawn(2, 0, TEAM_2)));

    assertTrue(new Check().isValid(board, TEAM_1));
    assertFalse(new Checkmate().isInMate(board, TEAM_1));
    assertTrue(new Checkmate().getScores(board).isEmpty());
  }

  @Test
  void playerInMate2() throws EngineException {
    pieces.addAll(List.of(util.makeKing(0, 0, TEAM_2),
        makePawn(1, 0, TEAM_1),
        makePawn(2, 1, TEAM_1),
        makePawn(1, 1, TEAM_1),
        makePawn(2, 0, TEAM_1)));

    board.setPieces(pieces);
    LOG.debug(board);

    assertFalse(checkmate.isInMate(board, TEAM_1));

    assertTrue(checkmate.isInMate(board, TEAM_2));



  }

  @Test
  void boardNotInCheckmate() throws EngineException {
    pieces.addAll(List.of(
        util.makeKing(0, 0, TEAM_1),
        makePawn(3, 0, TEAM_2)));

    board.setPieces(pieces);
    assertFalse(new Checkmate().isInMate(board, TEAM_2));
  }

  @Test
  void playerCanBlockCheckThusNotInMate() throws EngineException {
    pieces.addAll(List.of(
        util.makeKing(0, 0, TEAM_1),
        util.makeRook(1, 5, TEAM_1),
        util.makeRook(3, 0, TEAM_2),
        util.makeRook(3, 1, TEAM_2)
    ));

    board.setPieces(pieces);
    assertFalse(new Checkmate().isInMate(board, TEAM_1));
  }

  @Test
  void playerBlocksMate() throws EngineException {
    Piece rook1 = util.makeRook(1, 5, TEAM_1);
    Piece rook2 = util.makeRook(1, 0, TEAM_2);
    Piece rook3 = util.makeRook(2, 0, TEAM_2);
    Piece king1 = util.makeKing(0, 0, TEAM_1);
    Piece king2 = util.makeKing(7, 7, TEAM_2);
    pieces.addAll(List.of(king2, king1, rook1, rook3, util.makeRook(2, 1, TEAM_2),
        rook2));
    board.setPieces(pieces);
    LOG.debug(board);

    //assertFalse(CheckmateValidator.isInMate(board, TEAM_1));
    assertFalse(checkmate.isInMate(board, TEAM_1));

    //Some Movement
    ChessBoard copy = board.deepCopy();

    copy = board.makeHypotheticalMove(rook1, new Coordinate(1, 0));
    copy = copy.makeHypotheticalMove(rook3, new Coordinate(1, 0));
    //copy = copy.makeHypotheticalMove(king1, new Coordinate(1, 0));

    //assertTrue(CheckmateValidator.isInMate(board, TEAM_1));

    assertFalse(checkmate.isInMate(board, TEAM_2));

  }
}
