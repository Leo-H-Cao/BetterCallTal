package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.Exceptions.MovementGridException;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.Menu.LanguageModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class EditPieceTest extends DukeApplicationTest {

  private Scene myScene;
  private Stage myStage;
  private LanguageModal mylangmod;

  private EditorPiece editorPiece;

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
    editorPiece = new EditorPiece("pieceID");
  }

  @Test
  void testFiniteMovementChanges(){
    assertEquals(editorPiece.getTileStatus(2,3, 0), PieceGridTile.CLOSED);
    assertEquals(editorPiece.getTileStatus(6, 5, 0), PieceGridTile.CLOSED);
    editorPiece.setTile(2,3, PieceGridTile.OPEN, 0);
    editorPiece.setTile(6,5, PieceGridTile.OPEN, 0);
    assertEquals(PieceGridTile.OPEN, editorPiece.getTileStatus(2,3, 0));
    assertEquals(PieceGridTile.OPEN, editorPiece.getTileStatus(6, 5, 0));
    editorPiece.setTile(2,3, PieceGridTile.CLOSED, 0);
    editorPiece.setTile(6,5, PieceGridTile.CLOSED, 0);
    assertEquals(editorPiece.getTileStatus(2,3, 0), PieceGridTile.CLOSED);
    assertEquals(editorPiece.getTileStatus(6, 5, 0), PieceGridTile.CLOSED);
  }

  @Test
  void testInfiniteDiagonalMovemementChanges(){
    checkBoardAllClosed();
    editorPiece.setTile(6,0, PieceGridTile.INFINITY, 0);
    editorPiece.setTile(5,5, PieceGridTile.INFINITY, 0);
    assertEquals(PieceGridTile.INFINITY, editorPiece.getTileStatus(6,0, 0));
    assertEquals(PieceGridTile.INFINITY, editorPiece.getTileStatus(5, 5, 0));
    editorPiece.setTile(6,0, PieceGridTile.CLOSED, 0);
    editorPiece.setTile(5,5, PieceGridTile.CLOSED, 0);
    checkBoardAllClosed();
  }

  @Test
  void testNonDiagonalInfiniteMovement(){
    checkBoardAllClosed();
    editorPiece.setTile(1,3, PieceGridTile.INFINITY, 0);
    editorPiece.setTile(3,4, PieceGridTile.INFINITY, 0);
    assertEquals(PieceGridTile.INFINITY, editorPiece.getTileStatus(1,3, 0));
    assertEquals(PieceGridTile.INFINITY, editorPiece.getTileStatus(3, 4, 0));
    editorPiece.setTile(1,3, PieceGridTile.CLOSED, 0);
    editorPiece.setTile(3,4, PieceGridTile.CLOSED, 0);
    checkBoardAllClosed();
  }

  @Test
  void testMovementGridExceptions(){
    Exception infiniteMovementInvalidDirections = assertThrows(MovementGridException.class, () -> {
      editorPiece.setTile(3, 3, PieceGridTile.CLOSED, 0);
    });
    String editPIeceExpectedMessage = "Piece tile cannot be changed.";
    String actualMessage = infiniteMovementInvalidDirections.getMessage();
    assertTrue(actualMessage.contains(editPIeceExpectedMessage));

    Exception finiteMovementInvalidCoordinates = assertThrows(MovementGridException.class, () -> {
      editorPiece.setTile(3, 8, PieceGridTile.OPEN, 0);
    });

    String finiteMovementExpectedMessage = "Coordinates for movement are invalid";;
    actualMessage = finiteMovementInvalidCoordinates.getMessage();
    assertTrue(actualMessage.contains(finiteMovementExpectedMessage));
  }

  @Test
  void testEditorPieceImageChange(){
    Image defaultMainPieceImage = new Image("images/pieces/Default/white/kyle.png");
    Image defaultAltPieceImage = new Image("images/pieces/Default/black/cartman.png");
    assertEquals(defaultMainPieceImage.getUrl(), editorPiece.getImage(0).getValue().getUrl());
    assertEquals(defaultAltPieceImage.getUrl(), editorPiece.getImage(1).getValue().getUrl());
    Image newImage = new Image("images/pieces/Default/black/cartman.png");
    editorPiece.setImage(0, newImage);
    assertEquals(newImage, editorPiece.getImage(0).getValue());
    assertEquals(defaultAltPieceImage.getUrl(), editorPiece.getImage(1).getValue().getUrl());
  }

  private void checkBoardAllClosed(){
    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editorPiece.getTileStatus(i,j, 0), PieceGridTile.PIECE);
          continue;
        }
        assertEquals(editorPiece.getTileStatus(i,j, 0), PieceGridTile.CLOSED);
      }
    }
  }

}
