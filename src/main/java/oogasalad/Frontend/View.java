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
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class View {

	protected Scene myScene;
	protected Group myRoot;
	protected Rectangle2D myScreenSize;
	protected ResourceBundle myResources;
	private final MainView myMainView;

	public View(MainView mainView) {
		myScreenSize = Screen.getPrimary().getVisualBounds();
		myMainView = mainView;
		myRoot = new Group();
		try {
			myResources = ResourceBundle.getBundle(getClass().getSimpleName());
		} catch (MissingResourceException e) {
			// Do nothing if file isn't found
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
	 * @return The parent node of the scene which will be the direct child of myRoot
	 */
	protected abstract Node makeNode();

	protected MainView getMainView() {
		return myMainView;
	}

	protected Button makeExitButton() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, MainView.getLanguage().getString("exit"), "exit",
				(e) -> getView(HomeView.class).ifPresent(myMainView::changeScene));
		b.setPrefWidth(150);
		b.setPrefHeight(50);
		return b;
	}

	protected String getLanguageResource(String s) {
		return MainView.getLanguage().getString(getClass().getSimpleName() + s);
	}

	/**
	 * @param className Class to check getViews() for
	 * @return Optional View if it is found in myViews from MainView
	 */
	protected <T> Optional<View> getView(Class<T> className) {
		Optional<View> v = getMainView().getViews().stream().filter((e) -> className == e.getClass()).findFirst();
		if(v.isPresent()) {
			return v;
		} else {
			System.out.printf("%s not found in %s%n", className, myMainView.getViews());
			return Optional.empty();
		}
	}
}
