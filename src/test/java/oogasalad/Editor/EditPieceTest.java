package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.Exceptions.MovementGridException;
import oogasalad.Editor.ModelState.EditPiece.EditSinglePiece;
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
    myScene = mylangmod.makeScene();
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
    assertEquals(editorPiece.getTileStatus(2,3), PieceGridTile.CLOSED);
    assertEquals(editorPiece.getTileStatus(6, 5), PieceGridTile.CLOSED);
    editorPiece.setTileOpen(2,3);
    editorPiece.setTileOpen(6,5);
    assertEquals(PieceGridTile.OPEN, editorPiece.getTileStatus(2,3));
    assertEquals(PieceGridTile.OPEN, editorPiece.getTileStatus(6, 5));
    editorPiece.setTileClosed(2,3);
    editorPiece.setTileClosed(6,5);
    assertEquals(editorPiece.getTileStatus(2,3), PieceGridTile.CLOSED);
    assertEquals(editorPiece.getTileStatus(6, 5), PieceGridTile.CLOSED);
  }

  @Test
  void testInfiniteDiagonalMovemementChanges(){
    checkBoardAllClosed();

    editorPiece.setInfiniteTiles(-1, 1);
    editorPiece.setInfiniteTiles(1, -1);

    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editorPiece.getTileStatus(i,j), PieceGridTile.PIECE);
        }
        else if(i == j){
          if(i == 0 || i == 6){
            assertEquals(editorPiece.getTileStatus(i, j), PieceGridTile.INFINITY);
          }
          else{
            assertEquals(editorPiece.getTileStatus(i, j), PieceGridTile.OPEN);
          }
        }
        else{
          assertEquals(editorPiece.getTileStatus(i,j), PieceGridTile.CLOSED);
        }
      }
    }

    editorPiece.removeInfiniteTiles(-1, 1);
    editorPiece.removeInfiniteTiles(1, -1);
    checkBoardAllClosed();
  }

  @Test
  void testNonDiagonalInfiniteMovement(){
    checkBoardAllClosed();
    editorPiece.setInfiniteTiles(0, 1);
    editorPiece.setInfiniteTiles(-1, 0);

    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editorPiece.getTileStatus(i,j), PieceGridTile.PIECE);
        }
        else if( i == 3 && j <= 2){
          if(j == 0){
            assertEquals(editorPiece.getTileStatus(i, j),PieceGridTile.INFINITY);
          }
          else{
            assertEquals(editorPiece.getTileStatus(i, j),PieceGridTile.OPEN);
          }
        }
        else if(j == 3 && i<=2){
          if(i == 0){
            assertEquals(editorPiece.getTileStatus(i, j),PieceGridTile.INFINITY);
          }
          else{
            assertEquals(editorPiece.getTileStatus(i, j),PieceGridTile.OPEN);
          }
        }
        else{
          assertEquals(editorPiece.getTileStatus(i,j), PieceGridTile.CLOSED);
        }
      }
    }
    editorPiece.removeInfiniteTiles(0, 1);
    editorPiece.removeInfiniteTiles(-1,0);
    checkBoardAllClosed();
  }

  @Test
  void testMovementGridExceptions(){
    Exception infiniteMovementInvalidDirections = assertThrows(MovementGridException.class, () -> {
      editorPiece.setInfiniteTiles(-3, 1);
    });
    String infiniteMovementExpectedMessage = "Directions for infinite movement are invalid";
    String actualMessage = infiniteMovementInvalidDirections.getMessage();
    assertTrue(actualMessage.contains(infiniteMovementExpectedMessage));

    Exception finiteMovementInvalidCoordinates = assertThrows(MovementGridException.class, () -> {
      editorPiece.setTileOpen(3, 8);
    });

    String finiteMovementExpectedMessage = "Coordinates for finite movement are invalid";;
    actualMessage = finiteMovementInvalidCoordinates.getMessage();
    assertTrue(actualMessage.contains(finiteMovementExpectedMessage));
  }

  @Test
  void testEditorPieceImageChange(){
    assertNull(editorPiece.getImage(0));
    assertNull(editorPiece.getImage(1));
    Image newImage = new Image("images/pieces/black/rook.png");
    editorPiece.setImage(0, newImage);
    assertEquals(newImage, editorPiece.getImage(0));
    assertNull(editorPiece.getImage(1));

  }

  private void checkBoardAllClosed(){
    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editorPiece.getTileStatus(i,j), PieceGridTile.PIECE);
          continue;
        }
        assertEquals(editorPiece.getTileStatus(i,j), PieceGridTile.CLOSED);
      }
    }
  }

}
