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

	protected abstract Scene makeScene();
}
