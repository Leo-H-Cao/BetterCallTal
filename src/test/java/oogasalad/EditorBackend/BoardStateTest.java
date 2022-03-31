package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.Backend.ModelState.BoardState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardStateTest {
  private BoardState boardState;

  @BeforeEach
  void setup() {
    boardState = new BoardState();
  }

  @Test
  void testChangeBoardSize(){
    boardState.changeBoardSize(10,5);
    assertEquals(boardState.getBoardHeight(), 5);
    assertEquals(boardState.getBoardWidth(), 10);
  }

}
