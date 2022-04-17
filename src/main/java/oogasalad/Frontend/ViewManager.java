package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.EditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.LocalPlay.LocalGame;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.Menu.HostGame;
import oogasalad.Frontend.Menu.JoinGame;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.View;
import java.util.ResourceBundle;

public class ViewManager {
	public ViewManager(Stage stage, ResourceBundle rb) {
		BackendConnector.initBackend(rb);
		Stage myStage = stage;
		View homeView = new HomeView(myStage);
		View.addView(homeView);
		View.addView(new GameView(myStage));
		View.addView(new EditorView(myStage));
		View.addView(new HostGame(myStage));
		View.addView(new JoinGame(myStage));
		View.addView(new LocalGame(myStage));
		myStage.setScene(homeView.getScene());
	}
}
