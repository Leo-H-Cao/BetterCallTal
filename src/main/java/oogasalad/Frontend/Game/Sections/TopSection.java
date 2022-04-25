package oogasalad.Frontend.Game.Sections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.util.BackendConnector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class will handle the top section of the Game View's borderpane.
 * Meant to clean up the code / centralize all elements relevant to top section.
 */

public class TopSection {

    private final GridPane myGP;
    private static final String TITLE = "Title";
    private Button exit;
    private String Image_Path = "src/main/resources/images/pieces/Default/extra/";
    private int imgviewsize = 100;
    private int LEFTHBOXSPACING = 100;

    public TopSection() throws FileNotFoundException {

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

    private ArrayList<HBox> createTopHBoxes() throws FileNotFoundException {
        ArrayList<HBox> hboxes = new ArrayList<>();
        ImageView imageView2 = makeTalImg("Tal2.png");
        HBox left = new HBox(LEFTHBOXSPACING);
        left.setAlignment(Pos.CENTER_LEFT);
        exit = new Button("exit");
        left.getChildren().addAll(exit, imageView2);
        hboxes.add(left);

        HBox middle = new HBox();
        middle.setAlignment(Pos.CENTER);
        Text title = new Text(BackendConnector.getFrontendWord(TITLE, getClass()));
        title.setFont(new Font("Courier New", 30));
        title.setFill(Color.PURPLE);
        middle.getChildren().add(title);
        hboxes.add(middle);
        ImageView imgview = makeTalImg("Tal.png");
        HBox right = new HBox();
        right.setAlignment(Pos.CENTER);
        right.getChildren().add(imgview);
        hboxes.add(right);
        return hboxes;
    }

    private ImageView makeTalImg(String whichTal) throws FileNotFoundException {
        Image img = new Image(new FileInputStream(Image_Path + whichTal));
        ImageView imgview = new ImageView(img);
        imgview.setPreserveRatio(true);
        imgview.setSmooth(true);
        imgview.setCache(true);
        imgview.setFitWidth(imgviewsize);
        imgview.setFitHeight(imgviewsize);
        return imgview;
    }

    /**
     * Method to be called by GameView to return Gridpane
     * @return Top Section Gridpane
     */
    public GridPane getGP() {return myGP;}

    public void setExitButton(EventHandler<ActionEvent> e) {
        exit.setOnAction(e);
    }
}
