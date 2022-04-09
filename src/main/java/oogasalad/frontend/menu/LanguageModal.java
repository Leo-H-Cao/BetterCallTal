package oogasalad.frontend.menu;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import oogasalad.frontend.ViewInitializer;
import oogasalad.frontend.util.ButtonFactory;
import oogasalad.frontend.util.ButtonType;
import oogasalad.frontend.util.ResourceParser;
import oogasalad.frontend.util.View;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class LanguageModal extends View {
	private final List<String> AVAILABLE_LANGUAGES;
	private static ResourceBundle selectedLanguageView;
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
		super(stage);
		this.stage = stage;
		myResources = ResourceBundle.getBundle(getClass().getName());
		AVAILABLE_LANGUAGES = readAvailableLanguages();

		// Set the default language
		currentLanguage = myResources.getString("DefaultLanguage");
		selectedLanguageView = ResourceBundle.getBundle(getClass().getPackageName() + ".languages." + currentLanguage);
		selectLanguageLabel = new Label(selectedLanguageView.getString("SelectLanguage"));
		selectLanguageLabel.setId("selectLanguageTitle");
		selectLanguageLabel.setFont(new Font(32));
		fullscreenCheckBox = new CheckBox(selectedLanguageView.getString("Fullscreen"));
		fullscreenCheckBox.setId("fullscreen");
		startButton = ButtonFactory.makeButton(ButtonType.TEXT, selectedLanguageView.getString("Start"), "start",
				(e) -> {
					new ViewInitializer(stage, selectedLanguageView);
					stage.setMaximized(true);
					stage.setFullScreen(fullscreenCheckBox.isSelected());
				});
		stage.setTitle(selectedLanguageView.getString("SelectLanguage"));
	}

	/**
	 * @return The parent node of the scene which will be the direct child of myRoot
	 */
	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	/**
	 * @return The new scene to display on startup
	 */
	public Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setTop(wrap(selectLanguageLabel));
		ret.setCenter(makeCenterOptions());
		ret.setBottom(wrap(startButton));

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
				startButton.setText(selectedLanguageView.getString("Start"));
				fullscreenCheckBox.setText(selectedLanguageView.getString("Fullscreen"));
				currentLanguage = AVAILABLE_LANGUAGES.get(new_value.intValue());
			} catch (MissingResourceException e) {
				// do nothing if resource is not defined
			}
		});

		return choiceBox;
	}


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
