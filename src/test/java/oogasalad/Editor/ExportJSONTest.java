package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oogasalad.Editor.ExportJSON.ExportJSON;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.BoardState.TileEffect;
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
  private static final String CONFIGURATION_RESOURCE_PATH = "oogasalad/Editor/ExportJSON";

  private Scene myScene;
  private Stage myStage;
  private LanguageModal mylangmod;
  private ResourceBundle myResources;

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
    setResources();
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
  void testPiecesExport(){
    piecesState.createCustomPiece("123");
    piecesState.changePieceImage("123", new Image("images/pieces/black/rook.png"), 0);
    EditorPiece testPiece = piecesState.getPiece("123");

    testPiece.setTile(4, 2, PieceGridTile.OPEN);
    testPiece.setTile(6, 0, PieceGridTile.INFINITY);
    testPiece.setTile(5,1, PieceGridTile.OPEN);

    testPiece.setTile(0, 3, PieceGridTile.INFINITY);
    testPiece.setTile(1,3, PieceGridTile.OPEN);
    testPiece.setTile(2, 3, PieceGridTile.OPEN);

    testPiece.setTile(1,2, PieceGridTile.OPEN);
    testPiece.setTile(5, 6, PieceGridTile.OPEN);
    testPiece.setTile(2, 5, PieceGridTile.OPEN);

    boardState.setPieceStartingLocation("123", 3, 6, 0);
    boardState.setPieceStartingLocation("123", 4, 5, 0);
    exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
    exportJSON.writeToJSON();
//    assertEquals(exportJSON.getJSONTestString(), myResources.getString("exportPiecesJSONString"));
  }

  @Test
  void testTilesExport(){
    boardState.setTileEffect(1,2,TileEffect.FIRE);
    boardState.setTileEffect(6,5, TileEffect.SWAP);
    boardState.setTileEffect(2,6, TileEffect.BLACKHOLE);
    boardState.setTileEffect(3,4, TileEffect.PROMOTIONREVERSE);
    boardState.setTileImage(3,4, new Image("images/pieces/black/rook.png"));
    exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
    exportJSON.writeToJSON();
  }

  private void setResources() {
    try {
      myResources = ResourceBundle.getBundle(CONFIGURATION_RESOURCE_PATH, Locale.ENGLISH);
    } catch (NullPointerException | MissingResourceException e) {
      throw new IllegalArgumentException(String.format("Invalid resource file: %s", "GameRules"));
    }
  }
}
