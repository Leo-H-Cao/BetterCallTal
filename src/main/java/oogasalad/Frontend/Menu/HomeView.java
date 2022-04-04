package oogasalad.Frontend.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

    private VBox myVbox;
    private static final String[] ButtonOptions = {"Create", "Join", "Host"};
    private static final String TITLE = "Title";
    private static final int VBOX_SPACING = 5;
    private static final int SCREEN_SIZE = 500;

    public HomeView(MainView mainView) {
        super(mainView);
    }

    @Override
    protected Scene makeScene() {

        Scene scene = new Scene(myRoot, 500, 500);
        myVbox = new VBox(VBOX_SPACING);
        myVbox.setPrefSize(SCREEN_SIZE, SCREEN_SIZE);


        Text t = new Text(MainView.getLanguage().getString(getClass().getSimpleName() + TITLE));
        t.setFont(new Font(64));
        t.setWrappingWidth(SCREEN_SIZE);
        myVbox.getChildren().add(t);

        Collection<Button> buttons = makeButtons();

        //TODO: Figure out setVgrow
        //VBox.setVgrow(buttons[0], Priority.ALWAYS);
        //VBox.setVgrow(buttons[1], Priority.ALWAYS);
        //VBox.setVgrow(buttons[2], Priority.ALWAYS);

        myVbox.getChildren().addAll(buttons);
        myRoot.getChildren().add(myVbox);

        return scene;
    }

    private Collection<Button> makeButtons() {
        Collection<Button> ret = new ArrayList<>();
        ret.add(ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString(getClass().getSimpleName() + "Create"), "createButton",
                (e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == GameEditorView.class).forEach(getMainView()::changeScene)));
        ret.add(ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString(getClass().getSimpleName() + "Join"), "joinButton",
                (e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == GameView.class).forEach(getMainView()::changeScene)));
        ret.add(ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString(getClass().getSimpleName() + "Host"), "hostButton",
                (e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == GameView.class).forEach(getMainView()::changeScene)));

        ret.stream().forEach((b) -> {
            b.setPrefSize(SCREEN_SIZE, SCREEN_SIZE);
            b.setAlignment(Pos.CENTER);
        });

        return ret;
    }
}
