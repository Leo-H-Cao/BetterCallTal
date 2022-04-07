package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.Editor.Exceptions.InavlidPieceIDException;
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

  @Test
  void testChangePieceStartingLocation(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
    boardState.setPieceStartingLocation(pieceID, 0,  0);
    assertEquals(0, boardState.getPieceLocation(pieceID).getX());
    assertEquals(0, boardState.getPieceLocation(pieceID).getY());
    int newX = 6;
    int newY = 5;
    boardState.setPieceStartingLocation(pieceID, newX, newY);
    assertEquals(newX, boardState.getPieceLocation(pieceID).getX());
    assertEquals(newY, boardState.getPieceLocation(pieceID).getY());
  }

  @Test
  void testFindInvalidPieceIDInBoard(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
    String invalidID = "456";

    Exception noPieceException = assertThrows(InavlidPieceIDException.class, () -> {
      boardState.removePiece(invalidID);
    });
    String noPieceExpected = "Invalid pieceID, piece does not exist in board";
    String actualMessage = noPieceException.getMessage();
    assertTrue(actualMessage.contains(noPieceExpected));

    noPieceException = assertThrows(InavlidPieceIDException.class, () -> {
      boardState.getPieceLocation(invalidID);
    });
    actualMessage = noPieceException.getMessage();
    assertTrue(actualMessage.contains(noPieceExpected));

  }

}
