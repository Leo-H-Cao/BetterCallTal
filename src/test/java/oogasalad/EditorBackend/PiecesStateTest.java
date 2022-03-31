package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.Backend.ModelState.EditorPiece;
import oogasalad.Editor.Backend.ModelState.MovementRules;
import oogasalad.Editor.Backend.ModelState.PieceInfo;
import oogasalad.Editor.Backend.ModelState.PiecesState;
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

}
