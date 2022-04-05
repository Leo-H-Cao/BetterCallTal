package oogasalad.Frontend.Menu;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;



public class HostGame extends View {

    private static final String TITLE = "Title";
    private static final String PROMPT = "Prompt";

    public HostGame(MainView mainview) {
        super(mainview);
    }

    @Override
    protected Node makeNode() {
        StackPane sp = MakeStackPane();



        return sp;
    }

    private StackPane MakeStackPane() {
        StackPane sp = new StackPane();

        Text title = new Text(MainView.getLanguage().getString(getClass().getSimpleName() + TITLE));
        Text prompt = new Text(MainView.getLanguage().getString(getClass().getSimpleName() + PROMPT));

        sp.getChildren().addAll(title, prompt);
        return sp;
    }
}
