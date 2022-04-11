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
    myScene = mylangmod.getScene();
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

  @Test
  void testInfiniteDiagonalMovemementChanges(){
    checkBoardAllClosed();

    editSinglePiece.setInfiniteTiles(-1, 1);
    editSinglePiece.setInfiniteTiles(1, -1);

    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i,j), PieceGridTile.PIECE);
        }
        else if(i == j){
          if(i == 0 || i == 6){
            assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i, j), PieceGridTile.INFINITY);
          }
          else{
            assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i, j), PieceGridTile.OPEN);
          }
        }
        else{
          assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i,j), PieceGridTile.CLOSED);
        }
      }
    }

    editSinglePiece.removeInfiniteTiles(-1, 1);
    editSinglePiece.removeInfiniteTiles(1, -1);
    checkBoardAllClosed();
  }

  @Test
  void testNonDiagonalInfiniteMovement(){
    checkBoardAllClosed();
    editSinglePiece.setInfiniteTiles(0, 1);
    editSinglePiece.setInfiniteTiles(-1, 0);

    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i,j), PieceGridTile.PIECE);
        }
        else if( i == 3 && j <= 2){
          if(j == 0){
            assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i, j),PieceGridTile.INFINITY);
          }
          else{
            assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i, j),PieceGridTile.OPEN);
          }
        }
        else if(j == 3 && i<=2){
          if(i == 0){
            assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i, j),PieceGridTile.INFINITY);
          }
          else{
            assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i, j),PieceGridTile.OPEN);
          }
        }
        else{
          assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i,j), PieceGridTile.CLOSED);
        }
      }
    }
    editSinglePiece.removeInfiniteTiles(0, 1);
    editSinglePiece.removeInfiniteTiles(-1,0);
    checkBoardAllClosed();
  }

  private void checkBoardAllClosed(){
    for(int i = 0; i < 7; i ++){
      for(int j = 0; j < 7; j++){
        if(i == 3 && j ==3){
          assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i,j), PieceGridTile.PIECE);
          continue;
        }
        assertEquals(editSinglePiece.getPieceGrid().getTileStatus(i,j), PieceGridTile.CLOSED);
      }
    }
  }

}
