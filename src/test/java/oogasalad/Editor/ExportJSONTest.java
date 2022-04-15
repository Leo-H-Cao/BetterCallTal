package oogasalad.Editor;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.ExportJSON.ExportJSON;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.EditorBackend;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;
import oogasalad.Frontend.Menu.LanguageModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ExportJSONTest extends DukeApplicationTest {
  private Scene myScene;
  private Stage myStage;
  private LanguageModal mylangmod;

  private PiecesState piecesState;
  private BoardState boardState;
  private GameRulesState gameRulesState;
  private EditorBackend boardAndPieces;
  private ExportJSON exportJSON;

  @Override
  public void start(Stage stage) {
    mylangmod = new LanguageModal(stage);
    myStage = stage;
    myScene = mylangmod.getScene();
    myStage.setScene(myScene);
    myStage.show();
  }

  @BeforeEach
  void setup() {
   piecesState = new PiecesState();
   boardAndPieces = new EditorBackend();
   piecesState = boardAndPieces.getPiecesState();
   boardState = boardAndPieces.getBoardState();
   gameRulesState = new GameRulesState();
//   exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
  }

  @Test
  void initialExportTest(){
    EditorPiece testPiece= new EditorPiece("123");
    testPiece.setTile(4, 2, PieceGridTile.OPEN);
    testPiece.setTile(6, 0, PieceGridTile.INFINITY);
    testPiece.setTile(5,1, PieceGridTile.OPEN);

    testPiece.setTile(0, 3, PieceGridTile.INFINITY);
    testPiece.setTile(1,3, PieceGridTile.OPEN);
    testPiece.setTile(2, 3, PieceGridTile.OPEN);

    testPiece.setTile(1,2, PieceGridTile.OPEN);
    testPiece.setTile(5, 6, PieceGridTile.OPEN);
    testPiece.setTile(2, 5, PieceGridTile.OPEN);

    piecesState.createCustomPiece(12, 1, new Image("images/pieces/black/rook.png"), testPiece, "testPiece");
    boardState.setPieceStartingLocation("123", 3, 6);
    boardState.setPieceStartingLocation("123", 4, 5);
    exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
    exportJSON.writeToJSON();
  }

  @Test
  void testMovementJSONCreation(){

  }
}
