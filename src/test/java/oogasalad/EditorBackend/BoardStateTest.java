package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.ModelState.BoardAndPieces;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.MovementRules;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardStateTest {
  private BoardState boardState;
  private PiecesState piecesState;
  private BoardAndPieces boardAndPieces;

  @BeforeEach
  void setup() {
    boardAndPieces = new BoardAndPieces();
    piecesState = boardAndPieces.getPiecesState();
    boardState = boardAndPieces.getBoardState();

  }

  @Test
  void testChangeBoardSize(){
    assertEquals(boardState.getBoardHeight(), 8);
    assertEquals(boardState.getBoardWidth(), 8);
    int newHeight = 5;
    int newWidth = 10;
    boardState.changeBoardSize(newWidth,newHeight);
    assertEquals(newHeight, boardState.getBoardHeight());
    assertEquals(newWidth, boardState.getBoardWidth());
    newHeight = 12;
    newWidth = 3;
    boardState.changeBoardSize(newWidth, newHeight);
    assertEquals(newHeight, boardState.getBoardHeight());
    assertEquals(newWidth, boardState.getBoardWidth());
  }

//  @Test
//  void testChangePieceStartingLocation(){
//    String pieceID = "123";
//    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
//    assertEquals(0, piecesState.getPieceInfo(pieceID).getStartingPosX());
//    assertEquals(0, piecesState.getPieceInfo(pieceID).getStartingPosY());
//    int newX = 6;
//    int newY = 5;
//    boardState.setPieceStartingLocation(pieceID, newX, newY);
//    assertEquals(newX, piecesState.getPieceInfo(pieceID).getStartingPosX());
//    assertEquals(newY, piecesState.getPieceInfo(pieceID).getStartingPosY());
//  }


}
