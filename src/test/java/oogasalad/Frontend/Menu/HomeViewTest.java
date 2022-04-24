package oogasalad.Frontend.Menu;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class HomeViewTest extends DukeApplicationTest {

    private HomeView myHomeView;
    private ChoiceBox myLanguageSelector;
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

        myLanguageSelector = lookup("#languageSelector").query();
    }

    @Test
    void testOpenEditorView() {
        clickOn(lookup("#start").query());
        clickOn(lookup("#createButton").query());
    }

    @Test
    void testOpenHostView() {
        clickOn(lookup("#start").query());
        clickOn(lookup("#hostButton").query());
    }
    @Test
    void testOpenJoinView() {
        clickOn(lookup("#start").query());
        clickOn(lookup("#joinButton").query());
    }
    @Test
    void testOpenLocalView() {
        clickOn(lookup("#start").query());
        clickOn(lookup("#localButton").query());
    }
}
