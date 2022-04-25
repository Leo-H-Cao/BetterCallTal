package oogasalad.GamePlayer.ArtificialPlayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
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
    gameBot = new Bot(new TurnKeeper(new String[]{"human", "ai"}, board.getEndConditions()), "Hard");

  }


  @Test
  void testBotIdentifiesQueenBlunder() throws Throwable {
    List<Piece> pieces = board.getPieces();
    for(Piece p : pieces){
      if(p.getCoordinates().equals(new Coordinate(6, 7))){
        board.move(p, new Coordinate(5, 7)); //player 0 makes a pawn move
        break;
      };
    }

    //player 1's queen still on d3
    assertEquals(1, board.getTile(new Coordinate(5, 3)).getPieces().size());
    gameBot.getBotMove(board, 1);

    //bot should move player 1's queen away from attack
    assertTrue(board.getTile(new Coordinate(5, 3)).getPieces().isEmpty());

  }

}
