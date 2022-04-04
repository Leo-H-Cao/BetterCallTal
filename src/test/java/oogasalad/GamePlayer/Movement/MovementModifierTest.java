package oogasalad.GamePlayer.Movement;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovementModifierTest {


  private ChessBoard board;

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

    board = new ChessBoard(8, 8,  new Linear(players), players, List.of());
    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceThree = new Piece(new PieceData(new Coordinate(2, 0), "test3", 0, 2, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieces = List.of(pieceOne, pieceTwo, pieceThree);
    board.setPieces(pieces);

  }

  @Test
  void atomicTestHappy() {

  }

  @Test
  void atomicTestSad() {

  }
}