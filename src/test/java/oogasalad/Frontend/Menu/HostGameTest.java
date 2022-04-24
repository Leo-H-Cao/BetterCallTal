package oogasalad.Frontend.Menu;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import oogasalad.Frontend.util.BackendConnector;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

public class HostGameTest extends DukeApplicationTest {

    private Stage myStage;
    private HostGame myHostGame;
    private ResourceBundle myResources;
    private String filepath = "doc/games/TicTacToe.json";

    @Override
    public void start(Stage stage) {
        myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
        myHostGame = new HostGame(stage);
        BackendConnector.initBackend(myResources);
        stage.setScene(myHostGame.getScene());
        stage.show();
        myStage = stage;
    }

    @Test
    void testExit() {
        clickOn(lookup("#exit").query());
    }

    @Test
    void testColorSelect() {
        runAsJFXAction(() -> myHostGame.injectBoard(filepath));
        ChoiceBox myColorSelector = lookup("#colorchoice").query();
        select(myColorSelector, "Black");
    }

    @Test
    void testRoomID() {
        runAsJFXAction(() -> myHostGame.injectBoard(filepath));
        TextArea roomname = lookup("#RoomName").query();
        writeInputTo(roomname, "THIS IS WORKING");
        clickOn(lookup("#HostConfirm").query());
    }

    @Test
    void testStartButton() {
        runAsJFXAction(() -> myHostGame.injectBoard(filepath));
        TextArea roomname = lookup("#RoomName").query();
        writeInputTo(roomname, "THIS IS WORKING");
        clickOn(lookup("#HostConfirm").query());
        clickOn(lookup("#HostStart").query());
    }
}
