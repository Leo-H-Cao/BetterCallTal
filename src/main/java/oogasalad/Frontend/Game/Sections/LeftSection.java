package oogasalad.Frontend.Game.Sections;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;

/**
 * This class will control the right side of the Game View borderpane.
 */

public class LeftSection {

    private VBox SideVbox;

    public LeftSection(Runnable Switch) {
        SideVbox = makeSideVbox(Switch);
    }

    private VBox makeSideVbox(Runnable Switchrun){
        VBox vb = new VBox();

        Text t = new Text("Hey I'm here");
        t.setFont(new Font(64));

        Button flipbutton = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Flip", getClass()), "flip",
                (e) -> Switchrun.run());
        vb.getChildren().addAll(t, flipbutton);
        return vb;
    }

    public VBox getVbox() {return SideVbox;}
}
