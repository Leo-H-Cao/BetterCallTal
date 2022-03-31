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

	public Scene getScene() {
		return myScene;
	}

	public String getTitle() {
		return MainView.getLanguage().getString(this.getClass().getSimpleName() + "Title");
	}

	protected abstract Scene makeScene();
}
