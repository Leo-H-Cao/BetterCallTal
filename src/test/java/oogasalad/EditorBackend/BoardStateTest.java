//package oogasalad.EditorBackend;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import oogasalad.Editor.Backend.ModelState.BoardState;
//import oogasalad.Editor.Backend.ModelState.MovementRules;
//import oogasalad.Editor.Backend.ModelState.PiecesState;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class BoardStateTest {
//  private BoardState boardState;
//  private PiecesState piecesState;
//
//  @BeforeEach
//  void setup() {
//    boardState = new BoardState();
//    piecesState = new PiecesState();
//  }
//
//  @Test
//  void testChangeBoardSize(){
//    assertEquals(boardState.getBoardHeight(), 8);
//    assertEquals(boardState.getBoardWidth(), 8);
//    int newHeight = 5;
//    int newWidth = 10;
//    boardState.changeBoardSize(newWidth,newHeight);
//    assertEquals(newHeight, boardState.getBoardHeight());
//    assertEquals(newWidth, boardState.getBoardWidth());
//    newHeight = 12;
//    newWidth = 3;
//    boardState.changeBoardSize(newWidth, newHeight);
//    assertEquals(newHeight, boardState.getBoardHeight());
//    assertEquals(newWidth, boardState.getBoardWidth());
//  }
//
//}
