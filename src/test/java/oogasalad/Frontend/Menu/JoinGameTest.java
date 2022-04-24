package oogasalad.Frontend.Menu;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import oogasalad.Frontend.util.BackendConnector;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

public class JoinGameTest extends DukeApplicationTest {

    private JoinGame myJoinGame;
    private ResourceBundle myResources;

    @Override
    public void start(Stage stage) {
        myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
        myJoinGame = new JoinGame(stage);
        BackendConnector.initBackend(myResources);
        stage.setScene(myJoinGame.getScene());
        stage.show();
    }

    @Test
    void testExit() {
        clickOn(lookup("#exit").query());
    }

    @Test
    void testRoomID() {
        TextArea roomname = lookup("#RoomName").query();
        writeInputTo(roomname, "THIS IS WORKING");
        clickOn(lookup("#JoinConfirm").query());
    }

    @Test
    void testStartButton() {
        TextArea roomname = lookup("#RoomName").query();
        writeInputTo(roomname, "THIS IS WORKING");
        clickOn(lookup("#JoinConfirm").query());
        clickOn(lookup("#JoinStart").query());
    }
}
