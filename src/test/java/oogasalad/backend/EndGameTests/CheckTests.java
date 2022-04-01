package oogasalad.backend.EndGameTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GameClauses.CheckValidator;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing class for the Check class
 */
public class CheckTests {

  CheckValidator check;
  private ChessBoard board;
  private TurnCriteria turnCriteria;
  private EndCondition endCondition;

  private Player playerOne;
  private Player playerTwo;
  private Player[] players;

  private Piece pieceOne;
  private Piece pieceTwo;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {

    playerOne = new Player(0, new int[]{1});
    playerTwo = new Player(1, new int[]{0});
    players = new Player[]{playerOne, playerTwo};

    turnCriteria = new Linear(players);
    endCondition = new TwoMoves();
    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));

    check = new CheckValidator();

  }

  void pieceLocations(int row1, int col1, int row2, int col2) {

    pieceOne = new Piece(new PieceData(new Coordinate(row1, col1),
        "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);

    pieceTwo = new Piece(new PieceData(new Coordinate(row2, col2),
        "test2", 0, 1, true,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);

    pieces = List.of(pieceOne, pieceTwo);
    board.setPieces(pieces);
  }

  /**
   * isInCheck should return true because the king is in check
   */
  @Test
  void inCheck() throws OutsideOfBoardException {
    pieceLocations(0, 0, 1, 0);
    assertTrue(check.isInCheck(board, 1));
  }

  /**
   * isInCheck should return false because the king is not in check
   */
  @Test
  void notInCheck() throws OutsideOfBoardException {
    pieceLocations(0, 0, 3, 0);
    assertFalse(check.isInCheck(board, 0));
  }

  /**
   * This should never happen due to other parameters but testing this just in case
   * it should ever happen (coverage, sad-testing)
   */
  @Test
  void movesIntoCheck() {

  }

  /**
   * inCheck method should first
   */
  @Test
  void movesOutOfCheck() {

  }

}
