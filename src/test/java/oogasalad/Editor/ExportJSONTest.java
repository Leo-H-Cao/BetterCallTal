package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.test.util.ReflectionTestUtils;
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
  }

  @Test
  void testPiecesExport(){
    piecesState.createCustomPiece("123");
    piecesState.changePieceImage("123", new Image("images/pieces/black/rook.png"), 0);
    EditorPiece testPiece = piecesState.getPiece("123");
    testPiece.setPieceName("piece1");

    testPiece.setTile(4, 2, PieceGridTile.OPEN, 0);
    testPiece.setTile(6, 0, PieceGridTile.INFINITY, 0);
    testPiece.setTile(5,1, PieceGridTile.OPEN, 0);

    testPiece.setTile(0, 3, PieceGridTile.INFINITY, 0);
    testPiece.setTile(1,3, PieceGridTile.OPEN, 0);
    testPiece.setTile(2, 3, PieceGridTile.OPEN, 0);

    testPiece.setTile(1,2, PieceGridTile.OPEN, 0);
    testPiece.setTile(5, 6, PieceGridTile.OPEN, 0);
    testPiece.setTile(2, 5, PieceGridTile.OPEN, 0);

    boardState.setPieceStartingLocation("123", 3, 6, 1);
    boardState.setPieceStartingLocation("123", 4, 5, 0);

    piecesState.createCustomPiece("1234");
    piecesState.changePieceImage("1234", new Image("images/pieces/black/rook.png"), 1);
    EditorPiece testPiece2 = piecesState.getPiece("1234");
    testPiece2.setPieceName("piece2");
    testPiece2.setTile(4, 2, PieceGridTile.OPEN, 0);
    testPiece2.setTile(6, 0, PieceGridTile.INFINITY, 0);
    testPiece2.setTile(5,1, PieceGridTile.OPEN, 0);
    boardState.setPieceStartingLocation("1234", 4, 5, 1);

    exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
    File parentDir = new File("doc/testing_directory/json_export_test");
    exportJSON.writeToJSON(parentDir);
    assertEquals(myResources.getString("exportPiecesJSONString"), ReflectionTestUtils.getField(exportJSON, "MainJSONString"));
  }

  @Test
  void testTilesExport(){
    boardState.setTileEffect(1,2,TileEffect.FIRE);
    boardState.setTileEffect(6,5, TileEffect.SWAP);
    boardState.setTileEffect(2,6, TileEffect.BLACKHOLE);
    boardState.setTileImage(3,4, new Image("images/pieces/black/rook.png"));

    exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
    File parentDir = new File("doc/testing_directory/json_export_test");
    exportJSON.writeToJSON(parentDir);
    assertEquals(myResources.getString("exportTilesJSONString"), ReflectionTestUtils.getField(exportJSON, "MainJSONString"));
  }

  @Test
  void testGameRulesExport(){
    gameRulesState.setTurnCriteria("Linear");

    ArrayList<ArrayList<String>> winConditions = new ArrayList<>();
    ArrayList<String> winConditionsDefaultStalemate = new ArrayList<>();
    ArrayList<String> secondWinCondition = new ArrayList<>();
    winConditionsDefaultStalemate.add("Stalemate");
    secondWinCondition.add("Another End Condition");
    winConditions.add(winConditionsDefaultStalemate);
    winConditions.add(secondWinCondition);
    gameRulesState.setWinConditions(winConditions);

    ArrayList<String> colors = new ArrayList<>();
    colors.add("color1");
    colors.add("color2");
    gameRulesState.setColors(colors);

    HashMap<Integer, ArrayList<Integer>> teamOpponents = new HashMap<>();
    int team0 = 0;
    int team1 = 1;
    teamOpponents.put(team0, new ArrayList<>());
    teamOpponents.put(team1, new ArrayList<>());
    teamOpponents.get(team0).add(team1);
    teamOpponents.get(team1).add(team0);
    gameRulesState.setTeamOpponents(teamOpponents);

    ArrayList<ArrayList<String>> validStateCheckers = new ArrayList<>();
    ArrayList<String> defaultValidStateCheckers = new ArrayList<>();
    defaultValidStateCheckers.add("ValidStateChecker");
    validStateCheckers.add(defaultValidStateCheckers);
    gameRulesState.setValidStateCheckers(validStateCheckers);

    exportJSON = new ExportJSON(piecesState, gameRulesState, boardState);
    File parentDir = new File("doc/testing_directory/json_export_test");
    exportJSON.writeToJSON(parentDir);
    assertEquals(myResources.getString("exportRulesStateJSONString"), ReflectionTestUtils.getField(exportJSON, "MainJSONString"));
  }

  private void setResources() {
    try {
      myResources = ResourceBundle.getBundle(CONFIGURATION_RESOURCE_PATH, Locale.ENGLISH);
    } catch (NullPointerException | MissingResourceException e) {
      throw new IllegalArgumentException(String.format("Invalid resource file: %s", "GameRules"));
    }
  }
}
