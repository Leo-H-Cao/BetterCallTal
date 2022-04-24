package oogasalad.GamePlayer.Board;


import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;

/***
 * This class contains util functions that speed up the testing process for Board classes
 */

public class BoardTestUtil {

  public Piece makeKing(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "king" + team, 0, team, true,
        List.of(new Movement(List.of(new Coordinate(1, 0)), false)), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
  }

  public Piece makeRook(int row, int col, int team) {
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


  public Piece makePawn(int row, int col, int team) {
    return new Piece(new PieceData(new Coordinate(row, col),
        "pawn", 0, team, false,

        List.of(new Movement(List.of(new Coordinate(2*team-1, 0), new Coordinate(1, 0)), false)),
        List.of(new Movement(List.of(new Coordinate(2*team-1, 0), new Coordinate(1, 0)), false)),
        Collections.emptyList(), Collections.emptyList(), ""));

  }


}
