package oogasalad.Frontend;

import javafx.scene.Group;
import javafx.scene.Scene;

public abstract class SceneView {

	protected Scene myScene;
	protected Group myRoot;

	public SceneView() {
		myRoot = new Group();
		myScene = makeScene();
	}

	/**
	 * @return The stored scene in myScene
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * @return Stage title for this screen
	 */
	public String getTitle() {
		return MainView.getLanguage().getString(this.getClass().getSimpleName() + "Title");
	}

	/**
	 * @return The final scene that should be displayed by the frontend.
	 *     This function is called in the constructor and the return value is saved in myScene
	 */
	protected abstract Scene makeScene();
}
