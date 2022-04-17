package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Frontend.Menu.LanguageModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PiecesStateTest extends DukeApplicationTest {
  private PiecesState piecesState;

  private Scene myScene;
  private Stage myStage;
  private LanguageModal mylangmod;

  @Override
  public void start(Stage stage) {
    mylangmod = new LanguageModal(stage);
    myScene = mylangmod.getScene();
    myStage = stage;
    myStage.setScene(myScene);
    myStage.show();
  }

  @BeforeEach
  void setup() {
    piecesState = new PiecesState();
  }

  @Test
  void testCreatePiece(){
    String pieceID = "123";
    EditorPiece newPiece = piecesState.createCustomPiece(pieceID);
    assertEquals(pieceID, piecesState.getPiece(pieceID).getPieceID());
  }

  @Test
  void testChangePieceImage(){
    String pieceID = "123";
    Image rookImage = new Image("images/pieces/black/rook.png");
    piecesState.createCustomPiece(pieceID);
    piecesState.changePieceImage(pieceID, rookImage, 0);
    assertEquals(rookImage, piecesState.getPiece(pieceID).getImage(0));
    Image newImage = new Image("images/pieces/black/queen.png");
    piecesState.changePieceImage(pieceID,newImage, 0);
    assertEquals(newImage, piecesState.getPiece(pieceID).getImage(0));
  }

  @Test
  void testChangePieceAttributes(){
    String pieceID = "123";
    Image rookImage = new Image("images/pieces/black/rook.png");
    piecesState.createCustomPiece(pieceID);
    piecesState.setPieceName(pieceID, "my piece");
    piecesState.setPiecePointValue(pieceID, 1);
    piecesState.getPiece(pieceID).setPointValue(1);
    assertEquals("my piece", piecesState.getPiece(pieceID).getPieceName());
    assertEquals(1, piecesState.getPiece(pieceID).getPointValue());
    piecesState.setPiecePointValue(pieceID, 3);
    piecesState.setPieceName(pieceID, "new name");
    assertEquals("new name", piecesState.getPiece(pieceID).getPieceName());
    assertEquals(3, piecesState.getPiece(pieceID).getPointValue());

  }

  @Test
  void testGetPieces(){
    String pieceID = "123";
    piecesState.createCustomPiece(pieceID);
    String pieceID2 = "456";
    piecesState.createCustomPiece(pieceID2);
    List<EditorPiece> pieces = piecesState.getAllPieces();
    assertEquals(pieceID, pieces.get(0).getPieceID());
    assertEquals(pieceID2, pieces.get(1).getPieceID());
  }
}
