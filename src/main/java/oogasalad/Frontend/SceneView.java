package oogasalad.Frontend;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;

import java.util.stream.Stream;

public abstract class SceneView {

	protected Scene myScene;
	protected Group myRoot;
	private MainView myMainView;

	public SceneView(MainView mainView) {
		myMainView = mainView;
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

	protected MainView getMainView() {
		return myMainView;
	}

	protected Button makeExitButton() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString("exit"), "exitGameEditor",
				(e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == HomeView.class).forEach((c) ->
						getMainView().changeScene(c)));
		return b;
	}

	// TODO: fix method to streamline getting other classes
//	protected Stream<SceneView> getView(Class c) {
//		return getMainView().getViews().stream().filter((e) -> e.getClass() == ((SceneView)c).getClass());
//	}
}
