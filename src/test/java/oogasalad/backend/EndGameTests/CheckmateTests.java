package oogasalad.backend.EndGameTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.Editor.Movement.Movement;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.Frontend.GamePlayer.Board.Player;
import oogasalad.Frontend.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.Frontend.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.Frontend.GamePlayer.GameClauses.CheckValidator;
import oogasalad.Frontend.GamePlayer.GameClauses.CheckmateValidator;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;
import oogasalad.Frontend.GamePlayer.GamePiece.PieceData;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CheckmateValidator
 * @author Jose Santillan
 */
public class CheckmateTests {

  private static final int TEAM_1 = 1;
  private static final int TEAM_2 = 2;

  private ChessBoard board;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {

    Player playerOne = new Player(0, new int[]{1});
    Player playerTwo = new Player(1, new int[]{0});
    Player[] players = new Player[]{playerOne, playerTwo};

    Linear turnCriteria = new Linear(players);
    TwoMoves endCondition = new TwoMoves();

    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));
    pieces = new ArrayList<>();

  }

  Piece makeKing(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "king" + team, 0, team, true,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
  }

  Piece makePawn(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "pawn" + team, 0, team, false,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
  }

  Piece makeRook(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "rook" + team, 0, team, false,
        List.of(new Movement(List.of(new Coordinate(1, 0), new Coordinate(-1, 0), new Coordinate(0, 1), new Coordinate(0, -1)),
            true)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
  }

  /**
   * 2 Rooks pressuring the king (king cannot move nor capture rooks)
   * @throws OutsideOfBoardException
   */
  @Test
  void playerInMate() throws OutsideOfBoardException {

    //TODO DOES NOT WORK BECAUSE INFINITE MOVEMENT DOES NOT WORK YET (I THINK)
    pieces.addAll(List.of(makeKing(0, 0, 0), makeRook(2, 0, 1), makeRook(2, 1, 1)));
    board.setPieces(pieces);
    System.out.println(board);
    assertTrue(CheckmateValidator.isInMate(board, 0));

  }

  /**
   * Army of pawns attacking king
   * @throws OutsideOfBoardException
   */
  @Test
  void playerInMate2() throws OutsideOfBoardException {
    pieces.addAll(List.of(makeKing(0, 0, TEAM_1), makePawn(1, 0, TEAM_2), makePawn(2, 1, TEAM_2), makePawn(1, 1, TEAM_2), makePawn(2, 0, TEAM_2)));
    board.setPieces(pieces);
    System.out.println(board);
    assertTrue(CheckmateValidator.isInMate(board, 0));
  }


  @Test
  void boardNotInCheckmate() throws OutsideOfBoardException {
    pieces.addAll(List.of(makeKing(0, 0, TEAM_1), makePawn(3, 0, TEAM_2)));
    board.setPieces(pieces);
    assertFalse(CheckmateValidator.isInMate(board, 0));
  }

  @Test
  void playerCanMoveKingOutOfMate() throws OutsideOfBoardException {
    pieces.addAll(List.of(makeKing(0, 0, TEAM_1), makePawn(1, 0, TEAM_2), makePawn(1, 1, TEAM_2)));
    board.setPieces(pieces);
    System.out.println(board);
    assertFalse(CheckmateValidator.isInMate(board, TEAM_1));

  }

  @Test
  void playerCanMovePieceToRemoveMate() throws OutsideOfBoardException {
    pieces.addAll(List.of(makeKing(0, 0, TEAM_1), makeRook(1, 5, TEAM_1), makeRook(3, 0, TEAM_2), makeRook(3, 1, TEAM_2)));
    board.setPieces(pieces);
    System.out.println(board);
    assertFalse(CheckmateValidator.isInMate(board, TEAM_1));
  }

  @Test
  void playerBlocksMateThenEntersMateNextTurn() throws OutsideOfBoardException, InvalidMoveException {
    Piece rook1 = makeRook(1, 5, TEAM_1);
    Piece rook2 = makeRook(4, 0, TEAM_2);
    Piece king1 = makeKing(0, 0, TEAM_1);
    pieces.addAll(List.of(king1, rook1, makeRook(3, 0, TEAM_2), makeRook(3, 1, TEAM_2),
        rook2));
    board.setPieces(pieces);
    System.out.println(board);
    assertFalse(CheckmateValidator.isInMate(board, TEAM_1));
    rook1.move(board.getTile(new Coordinate(1, 0)));
    rook2.move(board.getTile(new Coordinate(1, 0)));
    king1.move(board.getTile(new Coordinate(1, 0)));
    assertTrue(CheckmateValidator.isInMate(board, TEAM_2));

  }

}
