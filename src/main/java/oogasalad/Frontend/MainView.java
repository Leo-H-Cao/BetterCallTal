package oogasalad.Frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.HomeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class MainView {
	// Bundle made static so all classes can easily access the language
	private static ResourceBundle langBundle;

	private Collection<SceneView> myViews;
	private Stage stage;


	public MainView(Stage stage, ResourceBundle rb) {
		myViews = new ArrayList<>();
		this.stage = stage;
		langBundle = rb;
		addView(new HomeView(this));
		addView(new GameView(this));
		addView(new GameEditorView(this));

		// Changes the current scene to the home page on initialization
		myViews.stream().filter((e) -> e.getClass() == HomeView.class).forEach(this::changeScene);
	}

	private void addView(SceneView view) {
		myViews.add(view);
	}

	public Collection<SceneView> getViews() {
		return myViews;
	}

	/**
	 * The Language Resource Bundle is public so that if the user changes languages, only need to change it in this class.
	 * @return Resource Bundle of selected language
	 */
	public static ResourceBundle getLanguage() {
		return langBundle;
	}

	public void changeScene(SceneView sceneClass) {
		stage.setScene(sceneClass.getScene());
		stage.setTitle(sceneClass.getTitle());
	}
}
