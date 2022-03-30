package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.Backend.ModelState.EditorPiece;
import oogasalad.Editor.Backend.ModelState.ModelState;
import oogasalad.Editor.Backend.ModelState.MovementRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardStateTest {
  private ModelState modelState;

  @BeforeEach
  void setup() {
    modelState = new ModelState();
  }

  @Test
  void testChangeBoardSize(){
    modelState.changeBoardSize(10,5);
    assertEquals(modelState.getBoardHeight(), 5);
    assertEquals(modelState.getBoardWidth(), 10);
  }

  @Test
  void testCreatePiece(){
    String pieceID = "123";
    EditorPiece newPiece = modelState.createCustomPiece(1, 1, "image.png", new MovementRules(), pieceID, "my piece");
    assertEquals(newPiece, modelState.getPiece(pieceID));
  }

}
