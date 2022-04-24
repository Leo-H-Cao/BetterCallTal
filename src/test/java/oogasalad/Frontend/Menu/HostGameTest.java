package oogasalad.Frontend.Menu;

import javafx.stage.Stage;
import oogasalad.Frontend.util.BackendConnector;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

public class HostGameTest extends DukeApplicationTest {

    private Stage myStage;
    private HostGame myHostGame;
    private ResourceBundle myResources;

    @Override
    public void start(Stage stage) {
        myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
        myHostGame = new HostGame(stage);
        BackendConnector.initBackend(myResources);
        stage.setScene(myHostGame.getScene());
        stage.show();
        myStage = stage;
    }

}
