package oogasalad.GamePlayer.Board.Tiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.BlackHoleAction;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlackHoleActionTest {
  ChessTile tile;
  ChessBoard board;

  @BeforeEach
  void setUp() {
    tile = new ChessTile();
    BlackHoleAction b = new BlackHoleAction();
    tile.setSpecialActions(List.of(b));
    Player[] players = {new Player(0, null), new Player(1, null)};
    TurnCriteria turnCriteria = new Linear(players);

    board = new ChessBoard(3, 3, turnCriteria, players, List.of());
  }

  @Test
  void test() throws OutsideOfBoardException {

    PieceData data = new PieceData(new Coordinate(0,0), "test", 0, 0, false, List.of(), List.of(), List.of(), List.of(), "i.img");
    Piece p = new Piece(data);

    tile.addPiece(p);
    assertTrue(tile.getPiece().isPresent());

    tile.executeActions(board);
    assertFalse(tile.getPiece().isPresent());

  }


}
