package oogasalad.GamePlayer.Movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.CustomMovements.Castling;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomMovementTest {

  // 0: no castle, 1: k castle, 2: q castle, 3: kq castle
  private List<ChessBoard> boards;

  private Player playerOne;
  private Player playerTwo;
  private Player[] players;

  private Piece whiteKing;
  private Piece whiteRookR;
  private Piece whiteRookL;
  private Piece whiteBlockerOne;
  private Piece whiteBlockerTwo;

  private Piece blackKing;
  private Piece blackRookR;
  private Piece blackRookL;
  private Piece blackBlockerOne;
  private Piece blackBlockerTwo;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, null);
    playerTwo = new Player(1, null);
    players = new Player[]{playerOne, playerTwo};

    IntStream.range(0, 3).forEach((i) -> boards.add(new ChessBoard(8, 2, new Linear(players), players, List.of())));

    whiteKing = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieceThree = new Piece(new PieceData(new Coordinate(2, 0), "test3", 0, 2, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), board);
    pieces = List.of(pieceOne, pieceTwo, pieceThree);
    board.setPieces(pieces);

  }

  @Test
  void castleTestNoCastle() {
    whiteKing = new Piece(new PieceData(new Coordinate(0, 0), "whiteKing", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""), boards.get(0));
  }

  @Test
  void castleTestKCastle() {

  }

  @Test
  void castleTestQCastle() {

  }

  @Test
  void castleTestKQCastle() {

  }

  @Test
  void castleTestSad() {

  }
}