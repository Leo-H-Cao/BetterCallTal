package oogasalad.Frontend.Menu;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.Frontend.Editor.EditorView;
import oogasalad.Frontend.Menu.LocalPlay.LocalGame;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.View;

/**
 * HomeView class will handle the navigation of the User from the home screen to the next page they seek.
 * Home Screen is the screen that displays "Build Game," "Join Game," and "Host Game"
 */
public class HomeView extends View {

    public HomeView(Stage stage) {
        super(stage);
        // Changes the current scene to the home page on app initialization
        getView(HomeView.class).ifPresent(this::changeScene);
        //createDefaultPieces();
    }

    @Override
    protected Node makeNode() {
        return makeLayout();
    }

    private Node makeLayout() {
        StackPane ret = new StackPane();
        ret.setPrefSize(myScreenSize.getWidth(), myScreenSize.getHeight());
        Node buttons = makeButtons();
        Node title = makeTitle();
        ret.getChildren().addAll(
                buttons,
                title
        );
        StackPane.setAlignment(buttons, Pos.CENTER);
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        return ret;
    }


    private Node makeTitle() {
        Label t = new Label(BackendConnector.getFrontendWord("Title", getClass()));
        t.setFont(new Font(64));
        t.setTextAlignment(TextAlignment.CENTER);
        return new Group(t);
    }

    private Node makeButtons() {
        GridPane buttonList = new GridPane();

        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Create", getClass()), "createButton",
                (e) -> getView(EditorView.class).ifPresent(this::changeScene)), 0, 0);

        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Join", getClass()), "joinButton",
                (e) -> getView(JoinGame.class).ifPresent(this::changeScene)), 0, 1);
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Host", getClass()), "hostButton",
                (e) -> getView(HostGame.class).ifPresent(this::changeScene)), 0, 2);

        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Local", getClass()), "localButton",
                e -> getView(LocalGame.class).ifPresent(this::changeScene)), 0, 3);

        buttonList.getChildren().forEach((b) -> {
            if(b instanceof Button) {
                ((Button)b).setAlignment(Pos.CENTER);
                ((Button)b).setPrefSize(400, 100);
            }
        });
        buttonList.setVgap(25);
        return new Group(buttonList);
    }
}
