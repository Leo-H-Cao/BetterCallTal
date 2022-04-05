package oogasalad.Frontend.Menu;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeViewTest extends DukeApplicationTest{
    private HomeView myHomeView;
    private ChoiceBox myLanguageSelector;
    private Scene myScene;
    private Stage myStage;
    private LanguageModal mylangmod;

    @Override
    public void start(Stage stage) {
        mylangmod = new LanguageModal(stage);
        myScene = mylangmod.makeScene();
        myStage = stage;
        myStage.setScene(myScene);
        myStage.show();

        myLanguageSelector = lookup("#languageSelector").query();
    }

    @Test
    void testOpenGameEditor() {
        clickOn(lookup("#start").query());

    }



}
