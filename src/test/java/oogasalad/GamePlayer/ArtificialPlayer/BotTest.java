package oogasalad.GamePlayer.ArtificialPlayer;

import java.io.IOException;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BotTest {
  Bot chessBot;
  ChessBoard board;


  @BeforeEach
  void setup() throws IOException {
    String JSONPath = "doc/AI_Testing/QueenBlunder.json";
    board = BoardSetup.createLocalBoard(JSONPath);

  }

  @Test
  void testBot(){
    int a = 0/2;

  }

}
