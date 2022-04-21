package oogasalad.GamePlayer.Board.History;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.CustomMovements.Castling;
import org.junit.jupiter.api.Test;

class HistoryTest {

  @Test
  void testToString() {
    Player playerOne = new Player(0, null);
    Player playerTwo = new Player(1, null);
    Player[] players = new Player[]{playerOne, playerTwo};
    History history = new History(new ChessBoard(8, 4, new Linear(players), players, List.of()), Set.of(new Piece(new PieceData(new Coordinate(0, 4), "whiteKing", 0, 0, true,
        List.of(new Castling()), Collections.emptyList(), List.of(), Collections.emptyList(), ""))),
        Set.of(new ChessTile()));
    assertEquals("Moved pieces: [whiteKing]; Updated tiles:[(0, 0): []]", history.toString());
  }
}