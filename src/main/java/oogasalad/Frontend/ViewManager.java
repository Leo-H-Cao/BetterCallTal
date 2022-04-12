package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.Menu.HostGame;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.View;
import java.util.ResourceBundle;

public class ViewManager {
	// Bundle made static so all classes can easily access the language
	private static ResourceBundle langBundle;
	private final Stage myStage;

	public ViewManager(Stage stage, ResourceBundle rb) {
		BackendConnector.initBackend();
		myStage = stage;
		langBundle = rb;
		View homeView = new HomeView(myStage);
		View.addView(homeView);
		View.addView(new GameView(myStage));
		View.addView(new GameEditorView(myStage));
		View.addView(new HostGame(myStage));
		myStage.setScene(homeView.getScene());
	}

	/**
	 * The Language Resource Bundle is public so that if the user changes languages, only need to change it in this class.
	 * @return Resource Bundle of selected language
	 */
	public static ResourceBundle getLanguage() {
		return langBundle;
	}
}
