package oogasalad.Frontend.Game.Sections;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.util.BackendConnector;

import java.util.ArrayList;

/**
 * This class will handle the top section of the Game View's borderpane.
 * Meant to clean up the code / centralize all elements relevant to top section.
 */

public class TopSection {
    private final GridPane myGP;
    private static final String TITLE = "Title";

    public TopSection() {

        myGP = new GridPane();
        setTopColConstraints();

        ArrayList<HBox> Hboxes = createTopHBoxes();
        myGP.add(Hboxes.get(0), 0, 0);
        myGP.add(Hboxes.get(1), 1, 0);
        myGP.add(Hboxes.get(2), 2, 0);
    }


    private void setTopColConstraints() {
        myGP.getColumnConstraints().add(new ColumnConstraints(500));
        myGP.getColumnConstraints().add(new ColumnConstraints(500));
        myGP.getColumnConstraints().add(new ColumnConstraints(500));
    }

    private ArrayList<HBox> createTopHBoxes() {
        ArrayList<HBox> hboxes = new ArrayList<>();
        HBox left = new HBox();
        left.setAlignment(Pos.CENTER_LEFT);
        Button Exit = new Button("Exit");
        left.getChildren().add(Exit);
        hboxes.add(left);

        HBox middle = new HBox();
        middle.setAlignment(Pos.CENTER);
        Text title = new Text(BackendConnector.getFrontendWord(TITLE, getClass()));
        title.setFont(new Font("Courier New", 30));
        title.setFill(Color.PURPLE);
        middle.getChildren().add(title);
        hboxes.add(middle);

        HBox right = new HBox(new Text("Image here maybe"));
        right.setAlignment(Pos.CENTER);
        hboxes.add(right);
        return hboxes;
    }

    /**
     * Method to be called by GameView to return Gridpane
     * @return Top Section Gridpane
     */
    public GridPane getGP() {return myGP;}
}
