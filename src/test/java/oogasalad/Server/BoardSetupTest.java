package oogasalad.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardSetupTest {
  ChessBoard myBoard;
  String JSON_FILE_PATH = "data/GameEngineResources/boardSetupTest.json";

  @BeforeEach
  void setup () throws IOException {
    BoardSetup setup = new BoardSetup(JSON_FILE_PATH);
    myBoard = setup.createBoard();
  }


  @Test
  void testBoardSize () {
    assertEquals(8, myBoard.getBoardLength());
    assertEquals(8, myBoard.getBoardHeight());
  }


  @Test
  void testPiecePlacement() throws OutsideOfBoardException {
    ChessTile pawnSquare = myBoard.getTile(new Coordinate(1, 0));
    assertEquals("Pawn", pawnSquare.getPiece().get().getName());
  }

  @Test
  void testGetPlayers(){
    assertEquals(0, myBoard.getPlayers()[0].teamID());
    assertEquals(1, myBoard.getPlayers()[1].teamID());
  }


}
