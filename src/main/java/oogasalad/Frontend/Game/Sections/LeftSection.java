package oogasalad.Frontend.Game.Sections;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label serverMessage;
    private String Flip = "Flip";
    private String Flip_ID = "flip";
    private String ServMess = "ServerMessage";
    private Double fontsize = 45.0;

    public LeftSection(Runnable Switch) {
        SideVbox = makeSideVbox(Switch);
    }

    private VBox makeSideVbox(Runnable Switchrun){
        serverMessage = new Label(BackendConnector.getFrontendWord(ServMess, getClass()));
        serverMessage.setFont(new Font(fontsize));
        serverMessage.wrapTextProperty();
        VBox vb = new VBox();

        Button flipbutton = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(Flip, getClass()), Flip_ID,
                (e) -> Switchrun.run());
        vb.getChildren().addAll(flipbutton);
        return vb;
    }

    /**
     * This method is called in GameView whenever the game is currently waiting on a response from server.
     * @param t True if message should be displayed, false else
     */
    public void dispServWait(Boolean t) {
        if (t) {
            SideVbox.getChildren().add(serverMessage);
        } else {
            SideVbox.getChildren().remove(serverMessage);
        }
    }
    public VBox getVbox() {return SideVbox;}
}
