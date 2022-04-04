package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.ModelState.EditorPiece;
import oogasalad.Editor.ModelState.MovementRules;
import oogasalad.Editor.ModelState.PieceInfo;
import oogasalad.Editor.ModelState.PiecesState;
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
    EditorPiece newPiece = piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece", 0, 0);
    PieceInfo newPieceInfo = newPiece.getPieceInfo();
    assertEquals(newPieceInfo, piecesState.getPieceInfo(pieceID));
  }

  @Test
  void testChangePieceStartingLocation(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece", 0, 0);
    assertEquals(0, piecesState.getPieceInfo(pieceID).getStartingPosX());
    assertEquals(0, piecesState.getPieceInfo(pieceID).getStartingPosY());
    int newX = 6;
    int newY = 5;
    piecesState.setPieceStart(pieceID, newX, newY);
    assertEquals(newX, piecesState.getPieceInfo(pieceID).getStartingPosX());
    assertEquals(newY, piecesState.getPieceInfo(pieceID).getStartingPosY());
  }

  @Test
  void testChangePieceImage(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece", 0, 0);
    assertEquals("image.png", piecesState.getPieceInfo(pieceID).getImageFile());
    String newImage = "image2.png";
    piecesState.changePieceImage(pieceID,newImage);
    assertEquals(newImage, piecesState.getPieceInfo(pieceID).getImageFile());
  }
<<<<<<< HEAD

=======
>>>>>>> 9e661200c405b8f5a0233731b9291bdf4ccb4834
}
