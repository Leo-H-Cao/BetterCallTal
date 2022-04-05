package oogasalad.backend.EndGameTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.Editor.Movement.Movement;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.Frontend.GamePlayer.Board.Player;
import oogasalad.Frontend.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;
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

  private ChessBoard board;

  private Piece player1King;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {

    Player playerOne = new Player(0, new int[]{1});
    Player playerTwo = new Player(1, new int[]{0});
    Player[] players = new Player[]{playerOne, playerTwo};

    Linear turnCriteria = new Linear(players);
    TwoMoves endCondition = new TwoMoves();

    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));

  }

  void pieceLocations(int row1, int col1, List<Integer> list, String code) {

    player1King = new Piece(new PieceData(new Coordinate(row1, col1),
        "test2", 0, 0, true,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);

    pieces = new ArrayList<>();
    pieces.add(player1King);

    for (int i = 0; i < list.size(); i += 2) {
      switch (code) {
        case "rook" -> {
          Piece newRook = makeRook(list.get(i), list.get(i + 1));
          pieces.add(newRook);
        }
        case "pawn" -> {
          Piece newPawn = makePawn(list.get(i), list.get(i + 1));
          pieces.add(newPawn);
        }
      }
    }

    board.setPieces(pieces);
  }
  Piece makePawn(int row, int col) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "test1", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
  }


  Piece makeRook(int row, int col) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "test1", 0, 1, false,
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
    pieceLocations(0, 0, List.of(2, 0, 2, 1), "rook");
    System.out.println(board);
    assertTrue(CheckmateValidator.isInMate(board, 0));

  }

  /**
   * More rooks to pressure king, he cannot move nor capture the rook on 1, 0
   * @throws OutsideOfBoardException
   */
  @Test
  void playerInMate2() throws OutsideOfBoardException {
    pieceLocations(0, 0, List.of(2, 0, 1, 0, 2, 1), "pawn");
    System.out.println(board);
    assertTrue(CheckmateValidator.isInMate(board, 0));
  }


  @Test
  void boardNotInCheckmate() {
    //ChessBoard board = new ChessBoard();
  }

  @Test
  void playerCanMoveKingOutOfMate() {
    //ChessBoard board = new ChessBoard();
  }

  @Test
  void playerCanMovePieceToRemoveMate() {
    //ChessBoard board = new ChessBoard();
  }

  @Test
  void playerBlocksMateThenEntersMateNextTurn() {

  }

}
