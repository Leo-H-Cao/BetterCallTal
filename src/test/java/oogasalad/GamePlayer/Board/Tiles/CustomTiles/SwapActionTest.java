package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import oogasalad.GamePlayer.Board.BoardTestUtil;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.BlackHoleAction;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.Swap;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SwapActionTest {
  ChessTile tile;
  ChessBoard board;
  BoardTestUtil util = new BoardTestUtil();

  @BeforeEach
  void setUp() throws OutsideOfBoardException {

    Player[] players = {new Player(0, null), new Player(1, null)};
    TurnCriteria turnCriteria = new Linear(players);

    board = new ChessBoard(3, 3, turnCriteria, players, List.of());
    tile = board.getTile(new Coordinate(1,1));
    Swap s = new Swap();
    tile.setSpecialActions(List.of(s));

    Piece piece1 = util.makePawn(1, 1, 1);
    tile.addPiece(piece1);
    Piece piece2 = util.makeRook(2, 2, 1);
    board.setPieces(List.of(piece1, piece2));
  }

  @Test
  void testExecuteAction(){
    tile.executeActions(board);
    List<Piece> pieces = board.getPieces();
    Piece piece1 = pieces.get(0);
    Piece piece2 = pieces.get(1);
    if(piece1.getName().equalsIgnoreCase("pawn")){
      assertEquals(piece1.getCoordinates(), new Coordinate(1, 1));
    }
    else{
      assertEquals(piece1.getCoordinates(), new Coordinate(2, 2));
    }

  }




}
