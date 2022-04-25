package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.EditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.LocalPlay.LocalGame;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.Menu.HostGame;
import oogasalad.Frontend.Menu.JoinGame;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.View;
import java.util.ResourceBundle;

public class ViewManager {

	public ViewManager(Stage stage, ResourceBundle rb) {
		BackendConnector.initBackend(rb);
		View homeView = new HomeView(stage);
		View.addView(homeView);
		View.addView(new GameView(stage));
		View.addView(new EditorView(stage));
		View.addView(new HostGame(stage));
		View.addView(new JoinGame(stage));
		View.addView(new LocalGame(stage));
		stage.setScene(homeView.getScene());
	}

}
