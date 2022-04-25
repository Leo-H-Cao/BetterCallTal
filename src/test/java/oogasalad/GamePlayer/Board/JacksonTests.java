package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import oogasalad.GamePlayer.Board.ChessBoard.ChessBoardData;
import oogasalad.GamePlayer.Server.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class JacksonTests {

  private static final Logger LOG = LogManager.getLogger(JacksonTests.class);
  private static final String filePath = "doc/games/";
  private final ObjectMapper mapper = RequestBuilder.objectMapperWithPTV();

  @Test
  void testChess() throws Exception {
    testJSON("test.json");
    testJSON("Atomic.json");
    testJSON("Antichess.json");
    testJSON("BlackHoleTileVariant.json");

  }

  @Test
  void testChess2() throws Exception {
    testJSON("Gravity.json");
    testJSON("KOTH.json");
    testJSON("LoseAllPieces.json");
    testJSON("Crazyhouse.json");
  }

  @Test
  void testChess3() throws Exception {
    testJSON("MoveAbsorption.json");
    testJSON("PresentationBoardUpdated.json");
    testJSON("PawnFootball.json");
    testJSON("RandomRemoval.json");
  }

  @Test
  void testTTT() throws Exception {
    testJSON("TicTacToeFiveButWithFourInARow.json");
    testJSON("TicTacToe.json");
    testJSON("TicTacToeFour.json");
  }


  void testJSON(String fileName) throws Exception {
    try {
      File file = new File(filePath + fileName);
      LOG.info("Reading file: " + file.getPath());
      ChessBoard board = BoardSetup.createLocalBoard(file.getAbsolutePath());
      String serialized = mapper.writeValueAsString(board.getBoardData());
      ChessBoardData deserialized = mapper.readValue(serialized, ChessBoardData.class);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }


}
