package oogasalad.Frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.HomeView;

import java.util.ResourceBundle;

public class MainView {
	// Bundle made static so all classes can easily access the language
	private static ResourceBundle langBundle;

	private GameView myGameView;
	private GameEditorView myEditorView;
	private HomeView myHomeView;
	private Stage stage;


	public MainView(Stage stage, ResourceBundle rb) {
		this.stage = stage;
		langBundle = rb;
		myGameView = new GameView();
		myEditorView = new GameEditorView();
		myHomeView = new HomeView();
		changeScene(myHomeView);
	}

	/**
	 * The Language Resource Bundle is public so that if the user changes languages, only need to change it in this class.
	 * @return Resource Bundle of selected language
	 */
	public static ResourceBundle getLanguage() {
		return langBundle;
	}

	private void changeScene(SceneView sceneClass) {
		stage.setScene(sceneClass.getScene());
		stage.setTitle(sceneClass.getTitle());
	}
}
