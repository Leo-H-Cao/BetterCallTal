package oogasalad.GamePlayer.ArtificialPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.Frontend.Game.TurnKeeper;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BotTest {
  Bot gameBot;
  ChessBoard board;


  @BeforeEach
  void setup() throws IOException {
    String JSONPath = "doc/testing_directory/AI_Testing/QueenBlunder.json";
    board = BoardSetup.createLocalBoard(JSONPath);
    gameBot = new Bot(new TurnKeeper(new String[]{"human", "ai"}), "Hard");

  }

  @Test
  void testBotIdentifiesQueenBlunder() throws Throwable {
    List<Piece> pieces = board.getPieces();
    for(Piece p : pieces){
      if(p.getCoordinates().equals(new Coordinate(6, 7))){
        board.move(p, new Coordinate(5, 7));
        break;
      };
    }
    gameBot.getBotMove(board, 1);

  }

}
