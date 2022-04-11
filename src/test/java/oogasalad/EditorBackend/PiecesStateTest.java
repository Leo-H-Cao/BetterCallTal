package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Editor.ModelState.PiecesState.LibraryPiece;
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
    myScene = mylangmod.makeScene();
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
    LibraryPiece newPiece = piecesState.createCustomPiece(1, 1, new Image("images/pieces/black/rook.png"), new MovementGrid(), pieceID, "my piece");
    assertEquals(pieceID, piecesState.getPiece(pieceID).getPieceID());
  }

  @Test
  void testChangePieceImage(){
    String pieceID = "123";
    Image rookImage = new Image("images/pieces/black/rook.png");
    piecesState.createCustomPiece(1, 1,rookImage , new MovementGrid(), pieceID, "my piece");
    assertEquals(rookImage, piecesState.getPiece(pieceID).getImage());
    Image newImage = new Image("images/pieces/black/queen.png");
    piecesState.changePieceImage(pieceID,newImage);
    assertEquals(newImage, piecesState.getPiece(pieceID).getImage());
  }

  @Test
  void testGetPieces(){
    String pieceID = "123";
    piecesState.createCustomPiece(1, 1, new Image("images/pieces/black/rook.png"), new MovementGrid(), pieceID, "my piece");
    String pieceID2 = "456";
    piecesState.createCustomPiece(1, 1, new Image("images/pieces/black/rook.png"), new MovementGrid(), pieceID2, "my piece2");
    List<LibraryPiece> pieces = piecesState.getAllPieces();
    assertEquals(pieceID, pieces.get(0).getPieceID());
    assertEquals(pieceID2, pieces.get(1).getPieceID());
  }
}
