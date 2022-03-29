package oogasalad.Frontend;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.Frontend.util.ResourceParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class LanguageModal {
	private final List<String> AVAILABLE_LANGUAGES;
	private ResourceBundle selectedLanguageView;
	private final Label selectLanguageLabel;
	private final Stage stage;
	private final Button startButton;
	private final ResourceBundle myResources;
	private final CheckBox fullscreenCheckBox;
	private String currentLanguage;

	/**
	 * Setups up default values and initializes the default language
	 * @param stage Stage created by main
	 */
	public LanguageModal(Stage stage) {
		myResources = ResourceBundle.getBundle(getClass().getName());
		this.stage = stage;
		AVAILABLE_LANGUAGES = readAvailableLanguages();

		// Set the default language
		currentLanguage = myResources.getString("DefaultLanguage");
		selectedLanguageView = ResourceBundle.getBundle(getClass().getPackageName() + ".languages." + currentLanguage);
		selectLanguageLabel = new Label(selectedLanguageView.getString("SelectLanguage"));
		selectLanguageLabel.setId("selectLanguageTitle");
		selectLanguageLabel.setFont(new Font(32));
		fullscreenCheckBox = new CheckBox(selectedLanguageView.getString("Fullscreen"));
		startButton = makeButton();
		stage.setTitle(selectedLanguageView.getString("SelectLanguage"));
	}

	/**
	 * @return The new scene to display on startup
	 */
	public Scene makeScene() {
		BorderPane root = new BorderPane();
		Scene ret = new Scene(root, ResourceParser.getInt(myResources, "Width"), ResourceParser.getInt(myResources, "Height"));
		ret.getStylesheets().add(Objects.requireNonNull(getClass().getResource("display.css")).toExternalForm());

		root.setTop(wrap(selectLanguageLabel));
		root.setCenter(makeCenterOptions());
		root.setBottom(wrap(startButton));

		return ret;
	}

	// Layout container for options
	private Node makeCenterOptions() {
		GridPane ret = new GridPane();
		ret.setAlignment(Pos.CENTER);
		ret.setVgap(50);
		ret.add(makeSelector(), 0, 0);
		ret.add(fullscreenCheckBox, 0, 1);
		return ret;
	}

	// Creates language selection dropdown node
	private Node makeSelector() {
		ChoiceBox<Object> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(AVAILABLE_LANGUAGES));

		choiceBox.setValue(myResources.getString("DefaultLanguage"));
		choiceBox.setId("languageSelector");

		choiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
			selectedLanguageView = ResourceBundle.getBundle(getClass().getPackageName() + ".languages." +
					AVAILABLE_LANGUAGES.get(new_value.intValue()));
			try {
				selectLanguageLabel.setText(selectedLanguageView.getString("SelectLanguage"));
				stage.setTitle(selectedLanguageView.getString("SelectLanguage"));
				startButton.setText(selectedLanguageView.getString("StartSLogo"));
				currentLanguage = AVAILABLE_LANGUAGES.get(new_value.intValue());
			} catch (MissingResourceException e) {
				// do nothing if resource is not defined
			}
		});

		return choiceBox;
	}

	// Creates start button
	private Button makeButton() {
		Button startButton = new Button(selectedLanguageView.getString("StartSLogo"));
		startButton.setOnAction((e) -> startHandler());
		return startButton;
	}

	// Function to run when start slogo is pressed
	private void startHandler() {
		stage.setX(0);
		stage.setY(0);
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setScene(vc.makeScene(currentLanguage, screenBounds.getWidth(), screenBounds.getHeight()));
		stage.setWidth(screenBounds.getWidth());
		stage.setHeight(screenBounds.getHeight());
		if(fullscreenCheckBox.isSelected()) {
			stage.setFullScreen(true);
		}
	}

	//
	// Reads available language files from the specified path; used in generating the language dropdown
	// @return list of language names
	private List<String> readAvailableLanguages() {
		ArrayList<String> ret = new ArrayList<>();
		String PATH_TO_LANGUAGES = myResources.getString("LanguagePath");
		try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir") + PATH_TO_LANGUAGES))) {
			paths.filter(Files::isRegularFile).forEach((file) -> {
				String fileName = String.valueOf(file.getFileName()).split("\\.")[0];
				ret.add(fileName); // Add name of file without the extension
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	// Simple layout helper to center n in an HBox
	private HBox wrap(Node n) {
		HBox ret = new HBox();
		ret.setPadding(new Insets(ResourceParser.getInt(myResources, "WrapperPadding")));
		ret.setAlignment(Pos.CENTER);
		ret.getChildren().add(n);
		return ret;
	}
}
