package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import oogasalad.Frontend.Game.TurnKeeper;
import oogasalad.GamePlayer.ArtificialPlayer.Bot;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceValueTest {
  ChessBoard board;
  PieceValue pieceValue = new PieceValue();

  @BeforeEach
  void setup() throws IOException {
    String JSONPath = "doc/testing_directory/AI_Testing/QueenBlunder.json";
    board = BoardSetup.createLocalBoard(JSONPath);

  }

  @Test
  void testPieceValueUtility(){
    double utility = pieceValue.getUtility(1, board);
    assertEquals(0, utility);
  }

}
