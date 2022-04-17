package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.Exceptions.InvalidPieceIDException;
import oogasalad.Editor.ModelState.EditorBackend;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Frontend.Menu.LanguageModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class BoardStateTest extends DukeApplicationTest {
  private BoardState boardState;
  private PiecesState piecesState;
  private EditorBackend editorBackend;

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
    editorBackend = new EditorBackend();
    piecesState = editorBackend.getPiecesState();
    boardState = editorBackend.getBoardState();
  }

  @Test
  void testChangeBoardSize(){
    assertEquals(boardState.getBoardHeight(), 8);
    assertEquals(boardState.getBoardWidth(), 8);
    int newHeight = 5;
    int newWidth = 10;
    boardState.changeBoardSize(newWidth,newHeight);
    assertEquals(newHeight, boardState.getBoardHeight());
    assertEquals(newWidth, boardState.getBoardWidth());
    newHeight = 12;
    newWidth = 3;
    boardState.changeBoardSize(newWidth, newHeight);
    assertEquals(newHeight, boardState.getBoardHeight());
    assertEquals(newWidth, boardState.getBoardWidth());
  }

  @Test
  void testChangePieceStartingLocation(){
    String pieceID = "123";
    int firstX = 5;
    int firstY = 6;
    piecesState.createCustomPiece(pieceID);
    boardState.setPieceStartingLocation(pieceID, 5,  6, 0);
    assertEquals(firstX, boardState.getPieceLocation(pieceID).getX());
    assertEquals(firstY, boardState.getPieceLocation(pieceID).getY());
    int newX = 2;
    int newY = 3;
    boardState.setPieceStartingLocation(pieceID, newX, newY, 0);
    assertEquals(newX, boardState.getPieceLocation(pieceID).getX());
    assertEquals(newY, boardState.getPieceLocation(pieceID).getY());
  }

  @Test
  void testFindInvalidPieceIDInBoard(){
    String pieceID = "123";
    EditorPiece piece1 = piecesState.createCustomPiece(pieceID);
    boardState.setPieceStartingLocation(piece1.getPieceID(), 3,4, 0);
    String invalidID = "456";

    //test piece ID for piece that does not exist
    Exception noPieceException = assertThrows(InvalidPieceIDException.class, () -> boardState.removePiece(invalidID));
//    Exception noPieceException = assertThrows(InavlidPieceIDException.class, () -> boardState.removePiece(invalidID));
    String noPieceExpected = "Invalid pieceID, piece does not exist in board";
    String actualMessage = noPieceException.getMessage();
    assertTrue(actualMessage.contains(noPieceExpected));

    boardState.removePiece(pieceID);

    //test removed piece from board
    noPieceException = assertThrows(InvalidPieceIDException.class, () -> boardState.getPieceLocation(pieceID));
    actualMessage = noPieceException.getMessage();
    assertTrue(actualMessage.contains(noPieceExpected));
  }

  @Test
  void testClearTile(){
    String pieceID = "123";
    EditorPiece piece1 = piecesState.createCustomPiece(pieceID);
    boardState.setPieceStartingLocation(piece1.getPieceID(), 3,4, 0);
    assertEquals(pieceID, boardState.getTile(3,4).getPieceID());
    boardState.clearTile(3,4);
    assertNull(boardState.getTile(3,4).getPieceID());
  }

}
