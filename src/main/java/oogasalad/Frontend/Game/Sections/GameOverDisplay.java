package oogasalad.Frontend.Game.Sections;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class GameOverDisplay {

    private Pane myDisplay;
    private ResourceBundle language;
    private Map<Integer, String> myTeams = new HashMap<>() {{
        put(0, "White");
        put(1, "Black");
    }};
    private Consumer<Node> removeCons;
    private static final String OK_BUTTON_ID = "OK";

    public GameOverDisplay(ResourceBundle rb, int winner, Consumer<Node> remove) {
        removeCons =remove;
        language = rb;
        myDisplay = new Pane();
        VBox vb = setUpDisplay(winner);
        myDisplay.getChildren().add(vb);
    }

    private VBox setUpDisplay(int winner) {
        VBox vb = new VBox();
        Text GameOver = new Text(getClass().getSimpleName() + "GameOver");
        GameOver.setFont(new Font(64));

        Text win = new Text(language.getString(getClass().getSimpleName() + myTeams.get(winner)));
        win.setFont(new Font(64));

        Button OK = ButtonFactory.makeButton(ButtonType.TEXT, language.getString(getClass().getSimpleName() + "Ok"), OK_BUTTON_ID,
                (e) -> {
                    removeCons.accept(myDisplay);
                });
        vb.getChildren().addAll(GameOver, win, OK);
        return vb;
    }

    public Pane getDisplay() {return myDisplay;}
}
