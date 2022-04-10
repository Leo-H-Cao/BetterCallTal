package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Editor.ModelState.EditPiece.EditSinglePiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.Menu.LanguageModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class EditPieceTest extends DukeApplicationTest {
  private Scene myScene;
  private Stage myStage;
  private LanguageModal mylangmod;

  private EditSinglePiece editSinglePiece;

  @Override
  public void start(Stage stage) {
    mylangmod = new LanguageModal(stage);
    myScene = mylangmod.makeScene();
    myStage = stage;
    myStage.setScene(myScene);
    myStage.show();
  }

  @BeforeEach
  void setup() {
    editSinglePiece = new EditSinglePiece("pieceID");
  }

  @Test
  void testFiniteMovementChanges(){
    assertEquals(editSinglePiece.getPieceGrid().getTileStatus(2,3), PieceGridTile.CLOSED);
    assertEquals(editSinglePiece.getPieceGrid().getTileStatus(6, 5), PieceGridTile.CLOSED);
    editSinglePiece.setTileOpen(2,3);
    editSinglePiece.setTileOpen(6,5);
    assertEquals(PieceGridTile.OPEN, editSinglePiece.getPieceGrid().getTileStatus(2,3));
    assertEquals(PieceGridTile.OPEN, editSinglePiece.getPieceGrid().getTileStatus(6, 5));
    editSinglePiece.setTileClosed(2,3);
    editSinglePiece.setTileClosed(6,5);
    assertEquals(editSinglePiece.getPieceGrid().getTileStatus(2,3), PieceGridTile.CLOSED);
    assertEquals(editSinglePiece.getPieceGrid().getTileStatus(6, 5), PieceGridTile.CLOSED);
  }

}
