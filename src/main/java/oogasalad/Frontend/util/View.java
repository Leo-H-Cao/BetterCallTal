package oogasalad.Frontend.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.ViewManager;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class View {
	private static final Logger LOG = LogManager.getLogger(View.class);

	protected Scene myScene;
	protected Group myRoot;
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	private final ViewManager myViewManager;

	public View(ViewManager viewManager) {
		myScreenSize = Screen.getPrimary().getVisualBounds();
		myViewManager = viewManager;
		myRoot = new Group();
		try {
			myResources = Optional.of(ResourceBundle.getBundle(getClass().getSimpleName()));
		} catch (MissingResourceException e) {
			myResources = Optional.empty();
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
		return ViewManager.getLanguage().getString(this.getClass().getSimpleName() + "Title");
	}

	/**
	 * @return The parent node of the scene which will be the direct child of myRoot
	 */
	protected abstract Node makeNode();

	protected ViewManager getViewManager() {
		return myViewManager;
	}

	protected Button makeExitButton() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, ViewManager.getLanguage().getString("exit"), "exit",
				(e) -> getView(HomeView.class).ifPresent(myViewManager::changeScene));
		b.setPrefWidth(150);
		b.setPrefHeight(50);
		return b;
	}

	protected String getLanguageResource(String s) {
		return ViewManager.getLanguage().getString(getClass().getSimpleName() + s);
	}

	/**
	 * @param className Class to check getViews() for
	 * @return Optional View if it is found in myViews from MainView
	 */
	protected <T> Optional<View> getView(Class<T> className) {
		Optional<View> v = getViewManager().getViews().stream().filter((e) -> className == e.getClass()).findFirst();
		if(v.isPresent()) {
			return v;
		} else {
			LOG.debug("%s not found in %s%n", className, myViewManager.getViews());
			return Optional.empty();
		}
	}
}
