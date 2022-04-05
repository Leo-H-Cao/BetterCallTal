package oogasalad.Frontend;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.ResourceParser;

import java.io.FileNotFoundException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class View {

	protected Scene myScene;
	protected Group myRoot;
	protected Rectangle2D myScreenSize;
	protected ResourceBundle myResources;
	private MainView myMainView;

	public View(MainView mainView) {
		myScreenSize = Screen.getPrimary().getVisualBounds();
		myMainView = mainView;
		myRoot = new Group();
		try {
			myResources = ResourceBundle.getBundle(getClass().getSimpleName());
		} catch (MissingResourceException e) {
			System.out.println("No ResourceBundle found for " + getClass());
		}
	}

	/**
	 * @return The stored scene in myScene
	 */
	public Scene getScene() {
		if(myScene == null) {
			myScene = createScene();
		}
		return myScene;
	}

	private Scene createScene() {
		Scene scene = new Scene(myRoot, myScreenSize.getWidth(), myScreenSize.getHeight());
		myRoot.getChildren().add(makeNode());
		return scene;
	}

	/**
	 * @return Stage title for this screen
	 */
	public String getTitle() {
		return MainView.getLanguage().getString(this.getClass().getSimpleName() + "Title");
	}

	/**
	 * @return The final node that should a direct child of .
	 *     This function is called in the constructor and the return value is saved in myScene
	 */
	protected abstract Node makeNode();

	protected MainView getMainView() {
		return myMainView;
	}

	protected Button makeExitButton() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString("exit"), "exit",
				(e) -> getMainView().getViews().stream().filter((c) -> c.getClass() == HomeView.class).forEach((c) ->
						getMainView().changeScene(c)));
		b.setPrefWidth(150);
		b.setPrefHeight(50);
		return b;
	}

	protected String getLanguageResource(String s) {
		return MainView.getLanguage().getString(getClass().getSimpleName() + s);
	}

	// TODO: fix method to streamline getting other classes
	protected <T> View getView(Class<T> className) {
		return getMainView().getViews().stream().filter((e) -> className == e.getClass()).findFirst().get();
	}
}
