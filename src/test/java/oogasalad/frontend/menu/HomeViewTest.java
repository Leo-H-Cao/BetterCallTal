package oogasalad.frontend.menu;

import javafx.stage.Stage;
import oogasalad.frontend.util.View;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

public class HomeViewTest extends DukeApplicationTest{
    private HomeView myHomeView;


    @Override
    public void start(Stage stage) {
        View.setLanguage(ResourceBundle.getBundle("resources/oogasalad/Frontend/menu/"));
        myHomeView = new HomeView(stage);
        View.addView(myHomeView);
    }

    @Test
    void testOpenGameEditor() {
        clickOn(lookup("#start").query());
    }
}
