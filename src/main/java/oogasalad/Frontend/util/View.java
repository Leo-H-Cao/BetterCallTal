package oogasalad.Frontend.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.ViewManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.util.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class View extends BackendConnector {
	private static Collection<View> myViews;
	private static boolean fullscreen = false;
	protected Scene myScene;
	protected Group myRoot;
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	private final Stage myStage;
	protected static final Logger LOG = LogManager.getLogger(View.class);

	public View(Stage stage) {
		if(myViews == null) {
			myViews = new ArrayList<>();
		}
		myScreenSize = Screen.getPrimary().getVisualBounds();
		myStage = stage;
		myRoot = new Group();
		try {
			myResources = Optional.of(ResourceBundle.getBundle(getClass().getSimpleName()));
		} catch (MissingResourceException e) {
			myResources = Optional.empty();
		}
	}
	public static void setFullscreen(boolean b) {
		fullscreen = b;
	}
	public static void addView(View v) {
		myViews.add(v);
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

	/**
	 * launches an explorer window for the user to choose the file where they want info to be loaded
	 * from.
	 *
	 * @return the chosen file by the user
	 */
	public File chooseLoadFile() {
		FileChooser fileChooser = new FileChooser();
		return fileChooser.showOpenDialog(myStage);
	}

	private Scene createScene() {
		Scene scene = new Scene(myRoot, myScreenSize.getWidth(), myScreenSize.getHeight());
		scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("display.css")).toExternalForm());
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

	protected Button makeExitButton() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, ViewManager.getLanguage().getString("exit"), "exit",
				(e) -> getView(HomeView.class).ifPresent(this::changeScene));
		b.setPrefWidth(150);
		b.setPrefHeight(50);
		return b;
	}

	protected void changeScene(View viewClass) {
		myStage.setScene(viewClass.getScene());
		myStage.setTitle(viewClass.getTitle());
		myStage.setFullScreen(fullscreen);
	}

	protected String getLanguageResource(String s) {
		return ViewManager.getLanguage().getString(getClass().getSimpleName() + s);
	}

	/**
	 * @param className Class to check getViews() for
	 * @return Optional View if it is found in myViews, Optional.empty otherwise
	 */
	protected <T> Optional<View> getView(Class<T> className) {
		return myViews.stream().filter((e) -> className == e.getClass()).findFirst();
	}
}
