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
    protected Scene makeScene() {
        Scene scene = new Scene(myRoot, myScreenSize.getWidth(), myScreenSize.getHeight());

        myRoot.getChildren().add(makeLayout());

        return scene;
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
        ret.setAlignment(buttons, Pos.CENTER);
        ret.setAlignment(title, Pos.TOP_CENTER);

        return ret;
    }


    private Node makeTitle() {
        Label t = new Label(MainView.getLanguage().getString(getClass().getSimpleName() + "Title"));
        t.setFont(new Font(64));
        t.setTextAlignment(TextAlignment.CENTER);
        Group ret = new Group(t);
        return ret;
    }

    private Node makeButtons() {
        GridPane buttonList = new GridPane();
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString(getClass().getSimpleName() + "Create"), "createButton",
                (e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == GameEditorView.class).forEach(getMainView()::changeScene)), 0, 0);
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString(getClass().getSimpleName() + "Join"), "joinButton",
                (e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == GameView.class).forEach(getMainView()::changeScene)), 0, 1);
        buttonList.add(ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString(getClass().getSimpleName() + "Host"), "hostButton",
                (e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == GameView.class).forEach(getMainView()::changeScene)), 0, 2);

        buttonList.getChildren().stream().forEach((b) -> {
            if(b instanceof Button) {
                ((Button)b).setAlignment(Pos.CENTER);
                ((Button)b).setPrefSize(400, 100);
            }
        });
        buttonList.setVgap(25);
        Group ret = new Group(buttonList);

        return ret;
    }
}
