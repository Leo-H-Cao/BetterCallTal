package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

//  @Test
//  void testCreatePiece(){
//    String pieceID = "123";
//    EditorPiece newPiece = piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
//    PieceInfo newPieceInfo = newPiece.getPieceInfo();
//    assertEquals(newPieceInfo, piecesState.getPieceInfo(pieceID));
//  }
//
//  @Test
//  void testChangePieceImage(){
//    String pieceID = "123";
//    piecesState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
//    assertEquals("image.png", piecesState.getPieceInfo(pieceID).getImageFile());
//    String newImage = "image2.png";
//    piecesState.changePieceImage(pieceID,newImage);
//    assertEquals(newImage, piecesState.getPieceInfo(pieceID).getImageFile());
//  }

  //test get all pieces + getpiece
}
