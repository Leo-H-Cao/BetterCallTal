package oogasalad.GamePlayer.Board.EndConditions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.ValidStateChecker.Check;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing class for the Check class
 */
public class CheckTests {

  private static final Logger LOG = LogManager.getLogger(CheckTests.class);

  Check check;
  private ChessBoard board;

  private Piece pieceOne;
  private Piece pieceTwo;

  @BeforeEach
  void setUp() {

    Player playerOne = new Player(0, new int[]{1});
    Player playerTwo = new Player(1, new int[]{0});
    Player[] players = new Player[]{playerOne, playerTwo};

    TurnCriteria turnCriteria = new Linear(players);
    EndCondition endCondition = new TwoMoves();
    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));

    check = new Check();

  }

  void pieceLocations(int row1, int col1, int row2, int col2) {

    pieceOne = new Piece(new PieceData(new Coordinate(row1, col1),
        "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)), List.of(new Movement(List.of(new Coordinate(1, 0)), false)), Collections.emptyList(), Collections.emptyList(), ""));

    pieceTwo = new Piece(new PieceData(new Coordinate(row2, col2),
        "test2", 0, 1, true,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)), List.of(new Movement(List.of(new Coordinate(1, 0)), false)), Collections.emptyList(), Collections.emptyList(), ""));

    List<Piece> pieces = List.of(pieceOne, pieceTwo);
    board.setPieces(pieces);
  }

  /**
   * isInCheck should return true because the king is in check
   */
  @Test
  void inCheck() throws OutsideOfBoardException {
    pieceLocations(0, 0, 1, 0);
    LOG.debug(board);

    //TEAM 1 is in check
    assertFalse(new Check().isValid(board, 1));
    //Team 2 is NOT in check
    assertTrue(new Check().isValid(board, 0));
  }

  /**
   * isInCheck should return false because the king is not in check
   */
  @Test
  void notInCheck() throws OutsideOfBoardException {
    pieceLocations(0, 0, 3, 0);
    assertTrue(new Check().isValid(board, 0));
    assertTrue(new Check().isValid(board, 1));
  }

  /**
   * This should never happen due to other parameters but testing this just in case
   * it should ever happen (coverage, sad-testing)
   */
  @Test
  void movesIntoCheck() throws OutsideOfBoardException {
    pieceLocations(0, 0, 3, 0);
    assertTrue(new Check().isValid(board, 1));

    //Can't actually move into check thanks to InvalidMoveException
    try {
      pieceTwo.move(board.getTile(new Coordinate(1, 0)), board);
      assertFalse(new Check().isValid(board, 1));
    } catch (InvalidMoveException ignored) {}


  }

  /**
   * inCheck method should first
   */
  @Test
  void movesOutOfCheck() throws EngineException {
    pieceLocations(0, 0, 1, 0);
    assertFalse(new Check().isValid(board, 1));
    ChessBoard newPosition = board.makeHypotheticalMove(pieceTwo, new Coordinate(2, 0));
    assertTrue(new Check().isValid(newPosition, 1));
  }
}
