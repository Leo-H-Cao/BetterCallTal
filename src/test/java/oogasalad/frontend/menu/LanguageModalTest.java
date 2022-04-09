package oogasalad.frontend.menu;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LanguageModalTest extends DukeApplicationTest {
	private ChoiceBox<String> myLanguageSelector;
	private LanguageModal myLanguageModal;
	private Scene myScene;
	private Stage myStage;
	private ResourceBundle myResources;


	@Override
	public void start (Stage stage) {
		myLanguageModal = new LanguageModal(stage);
		myScene = myLanguageModal.getScene();
		myStage = stage;
		myStage.setScene(myScene);
		myStage.show();
		myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");


		myLanguageSelector = lookup("#languageSelector").query();
	}


	@Test
	void testSelectFrench() {
		select(myLanguageSelector, "French");
		assertEquals(myStage.getTitle(), ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.French").getString("SelectLanguage"));
	}


	@Test
	void testSelectEnglish() {
		select(myLanguageSelector, "English");
		assertEquals(myStage.getTitle(), myResources.getString("SelectLanguage"));
	}

	@Test
	void testStartGame() {
		clickOn(lookup("#start").query());
		assertEquals(myStage.getTitle(), myResources.getString("HomeViewTitle"));
	}

	@Test
	void testFullscreen() {
		clickOn(lookup("#fullscreen").query());
		clickOn(lookup("#start").query());
		assertTrue(myStage.isFullScreen());
	}
}
