package oogasalad.Frontend.Menu;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * HomeView class will handle the navigation of the User from the home screen to the next page they seek.
 * Home Screen is the screen that displays "Build Game," "Join Game," and "Host Game"
 */

public class HomeView extends View {

    public HomeView(MainView mainView) {
        super(mainView);
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
        Label t = new Label(getLanguageResource("Title"));
        t.setFont(new Font(64));
        t.setTextAlignment(TextAlignment.CENTER);
        return new Group(t);
    }

    private Node makeButtons() {
        GridPane buttonList = new GridPane();
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource("Create"), "createButton",
                (e) -> getView(View.class).ifPresent(getMainView()::changeScene)), 0, 0);
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource("Join"), "joinButton",
                (e) -> getView(GameView.class).ifPresent(getMainView()::changeScene)), 0, 1);
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource("Host"), "hostButton",
                (e) -> getView(HostGame.class).ifPresent(getMainView()::changeScene)), 0, 2);

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
