package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import oogasalad.Editor.ModelState.PiecesState.EditorPiece;
import oogasalad.Editor.ModelState.PiecesState.MovementRules;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PiecesStateTest {
  private PiecesState piecesState;

  @BeforeEach
  void setup() {
    piecesState = new PiecesState();
  }

  @Test
  void testCreatePiece(){
    String pieceID = "123";
    EditorPiece newPiece = piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
    assertEquals(pieceID, piecesState.getPiece(pieceID).getPieceID());
  }

  @Test
  void testChangePieceImage(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
    assertEquals("image.png", piecesState.getPiece(pieceID).getImage());
    String newImage = "image2.png";
    piecesState.changePieceImage(pieceID,newImage);
    assertEquals(newImage, piecesState.getPiece(pieceID).getImage());
  }

  @Test
  void testGetPieces(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
    String pieceID2 = "456";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID2, "my piece2");
    List<EditorPiece> pieces = piecesState.getAllPieces();
    assertEquals(pieceID, pieces.get(0).getPieceID());
    assertEquals(pieceID2, pieces.get(1).getPieceID());
  }
}
