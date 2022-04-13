package oogasalad.Editor;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
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
   exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
  }

//  @Test
//  void initialExportTest(){
//    piecesState.createCustomPiece(1, 1, new Image("images/pieces/black/rook.png"), new EditorPiece("123"), "piece1");
//    exportJSON.writeToJSON();
//  }

}
