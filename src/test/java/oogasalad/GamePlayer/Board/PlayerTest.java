package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import oogasalad.GamePlayer.GamePiece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

  private Player playerOne;
  private Player playerTwo;
  private Player playerThree;

  private Piece pieceOne;
  private Piece pieceTwo;
  private Piece pieceThree;
  private List<Piece> pieces;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, new int[]{1, 2});
    playerTwo = new Player(1, new int[]{2});
    playerThree = new Player(2, new int[]{0, 2});
  }

  @Test
  void testPlayerToString() {
    assertEquals(playerOne.toString(), "0: [1, 2]");
    assertEquals(playerTwo.toString(), "1: [2]");
    assertEquals(playerThree.toString(), "2: [0, 2]");
  }
}