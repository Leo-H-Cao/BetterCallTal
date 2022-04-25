package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckmateLossTest {
  ChessBoard board;
  CheckmateLoss checkmateLoss = new CheckmateLoss();

  @BeforeEach
  void setup() throws IOException {
    String JSONPath = "doc/testing_directory/AI_Testing/QueenBlunder.json";
    board = BoardSetup.createLocalBoard(JSONPath);

  }

  @Test
  void testCheckmateUtility(){
    double utility = checkmateLoss.getUtility(1, board);
    assertEquals(Integer.MAX_VALUE, utility);
  }

}
