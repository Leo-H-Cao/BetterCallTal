package oogasalad.GamePlayer.TileActionTests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.Checkmate;
import oogasalad.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.ValidStateChecker.Check;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;
import oogasalad.GamePlayer.Board.EndConditions.CheckmateTests;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DemoteTests {
  private static final Logger LOG = LogManager.getLogger(CheckmateTests.class);

  private static final int TEAM_1 = 1;
  private static final int TEAM_2 = 0;

  ValidStateChecker Check = new Check();
  oogasalad.GamePlayer.Board.EndConditions.Checkmate Checkmate = new Checkmate();

  private ChessBoard board;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {

    Player playerOne = new Player(TEAM_1, new int[]{TEAM_2});
    Player playerTwo = new Player(TEAM_2, new int[]{TEAM_1});
    Player[] players = new Player[]{playerOne, playerTwo};

    Linear turnCriteria = new Linear(players);
    TwoMoves endCondition = new TwoMoves();

    board = new ChessBoard(8, 8, turnCriteria, players, List.of(endCondition));
    pieces = new ArrayList<>();
  }

  Piece makeKing(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "king" + team, 0, team, true,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
  }

  Piece makePawn(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "pawn" + team, 0, team, false,

        List.of(new Movement(List.of(new Coordinate(-1, 0), new Coordinate(1, 0)), false)),
        List.of(new Movement(List.of(new Coordinate(-1, 0), new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), ""));

  }

  Piece makeRook(int row, int col, int team) {
    Movement rookMovement = new Movement(List.of(
        new Coordinate(1, 0),
        new Coordinate(-1, 0),
        new Coordinate(0, 1),
        new Coordinate(0, -1)),
        true);

    return new Piece(new PieceData(new Coordinate(row, col),
        "rook" + team, 0, team, false,
        List.of(rookMovement),
        List.of(rookMovement), Collections.emptyList(),
        Collections.emptyList(), ""));
  }
  @Test
  void rookToTile() {

  }
}
