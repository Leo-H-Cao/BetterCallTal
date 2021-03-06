package oogasalad.Frontend.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.Frontend.Menu.HomeView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class View extends BackendConnector {

	protected static final Logger LOG = LogManager.getLogger(View.class);
	private static Collection<View> myViews;
	private static boolean fullscreen;
	protected Scene myScene;
	protected Group myRoot;
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	protected final Stage myStage;

	public View(Stage stage) {
		fullscreen = false;
		if(myViews == null) {
			myViews = new ArrayList<>();
		}
		myScreenSize = Screen.getPrimary().getVisualBounds();
		myStage = stage;
		myRoot = new Group();
		try {
			myResources = Optional.of(ResourceBundle.getBundle(getClass().getName()));
		} catch (MissingResourceException e) {
			myResources = Optional.empty();
		}
	}

	/**
	 * Sets the stage to be full screen
	 * @param b should the screen be set to fullscreen
	 */
	public static void setFullscreen(boolean b) {
		fullscreen = b;
	}

	/**
	 * adds a view to myViews
	 * @param v View to be added to myViews
	 */
	public static void addView(View v) {
		myViews.add(v);
	}

	/**
	 * Resets all the views in the myViews array
	 */
	protected void resetView() {
		myViews.clear();
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
		return BackendConnector.getFrontendWord("Title", getClass());
	}

	/**
	 * @return The parent node of the scene which will be the direct child of myRoot
	 */
	protected abstract Node makeNode();

	protected Button makeExitButton() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("exit"), "exit",
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

	/**
	 * @param className Class to check getViews() for
	 * @return Optional View if it is found in myViews, Optional.empty otherwise
	 */
	protected <T> Optional<View> getView(Class<T> className) {
		return myViews.stream().filter((e) -> className == e.getClass()).findFirst();
	}

	protected Node makeLabelGroup(String s, Integer size) {
		Label l = new Label(s);
		l.setFont(new Font(size));
		l.setTextAlignment(TextAlignment.CENTER);
		return new Group(l);
	}

	protected Node makeExitGroup() {
		Button Exit = makeExitButton();
		return new Group(Exit);
	}

	protected ChoiceBox<String> makePackageSelectGroup() {
		String[] langoptions = getFrontendWord("Packages").split(",");
		ChoiceBox<String> packages = new ChoiceBox<>();
		packages.setId("PackageSelect");
		packages.getItems().addAll(langoptions);
		packages.setValue(langoptions[0]);
		return packages;
	}
}
