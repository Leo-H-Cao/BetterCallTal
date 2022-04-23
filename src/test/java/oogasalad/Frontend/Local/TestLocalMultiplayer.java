package oogasalad.Frontend.Local;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;
import javafx.stage.Stage;
import oogasalad.Frontend.LocalPlay.LocalGame;
import oogasalad.Frontend.util.BackendConnector;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class TestLocalMultiplayer extends DukeApplicationTest {

  private Stage myStage;
  private LocalGame myLocalGame;
  private ResourceBundle myResources;

  @Override
  public void start(Stage stage) {
    myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
    myLocalGame = new LocalGame(stage);
    BackendConnector.initBackend(myResources);
    stage.setScene(myLocalGame.getScene());
    stage.show();
    myStage = stage;
  }

  @Test
  void testMultiplayerLocalPlay() {
    clickOn(lookup("#multiplayerButton").query());
    clickOn(lookup("#first").query());
//    clickOn(lookup("#upload").query());
    myLocalGame.injectBoard("doc/games/TicTacToe.json");
    clickOn(lookup("#done").query());
  }

  @Test
  void testSinglePlayerLocalPlay() {
    clickOn(lookup("#singleplayerButton").query());
    clickOn(lookup("#easyAI").query());
    clickOn(lookup("#first").query());
//    clickOn(lookup("#upload").query());
    myLocalGame.injectBoard("doc/games/TicTacToe.json");
    clickOn(lookup("#done").query());
  }

  @Test
  void testSinglePlayerLocalPlayNoGameUploaded() {
    clickOn(lookup("#singleplayerButton").query());
    clickOn(lookup("#easyAI").query());
    clickOn(lookup("#first").query());
//    clickOn(lookup("#upload").query());
    //myLocalGame.injectBoard("doc/games/TicTacToe.json");
    try {
      clickOn(lookup("#done").query());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSinglePlayerLocalPlayNoAISelected() {
    clickOn(lookup("#singleplayerButton").query());
    //clickOn(lookup("#easyAI").query());
    clickOn(lookup("#first").query());
//    clickOn(lookup("#upload").query());
    myLocalGame.injectBoard("doc/games/TicTacToe.json");
    try {
      clickOn(lookup("#done").query());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
