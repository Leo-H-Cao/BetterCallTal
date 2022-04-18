package oogasalad.Frontend.Game.Sections;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class GameOverDisplay {

    private VBox myDisplay;
    private Map<Double, String> myTeams = new HashMap<>() {{
        put(0.0, "White");
        put(1.0, "Black");
        put(.5, "Draw");
    }};
    private Consumer<Node> removeCons;
    private static final String OK_BUTTON_ID = "OK";
    private static Double WINVALUE = 1.0;
    private static Double WIDTH = 500.0;
    private static Double HEIGHT = 500.0;

    public GameOverDisplay(Map<Integer, Double> scores, Consumer<Node> remove) {
        removeCons =remove;
        Double winner = findWinner(scores);
        myDisplay = setUpDisplay(winner);
    }

    private Double findWinner(Map<Integer, Double> scores){
        double ret = .5;
        for (int k : scores.keySet()){
            if (scores.get(k).equals(WINVALUE)) {
                ret = k;
                break;
            }
        }
        return ret;
    }

    private VBox setUpDisplay(Double winner) {
        VBox vb = new VBox();
        vb.setMaxSize(WIDTH, HEIGHT);
        Text GameOver = new Text(BackendConnector.getFrontendWord("GameOver", getClass()));
        GameOver.setFont(new Font("Times New Roman",64));
        GameOver.setFill(Color.WHITE);

        Text win = new Text(BackendConnector.getFrontendWord(myTeams.get(winner), getClass()));
        win.setFont(new Font("Times New Roman",64));
        win.setFill(Color.CORAL);

        Button OK = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Ok", getClass()), OK_BUTTON_ID,
                (e) -> {
                    removeCons.accept(myDisplay);
                });
        vb.getChildren().addAll(GameOver, win, OK);
        return vb;
    }

    public VBox getDisplay() {return myDisplay;}
}
