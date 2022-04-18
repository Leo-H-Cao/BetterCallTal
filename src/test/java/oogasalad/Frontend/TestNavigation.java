package oogasalad.Frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Frontend.Menu.LanguageModal;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNavigation extends DukeApplicationTest {
	private Stage myStage;
	private ResourceBundle myResources;


	@Override
	public void start (Stage stage) {
		LanguageModal myLanguageModal = new LanguageModal(stage);
		Scene myScene = myLanguageModal.getScene();
		myStage = stage;
		myStage.setScene(myScene);
		myStage.show();
		myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
		clickOn(lookup("#start").query());
	}

	@Test
	void testEditor() {
		clickOn(lookup("#createButton").query());
		assertEquals(myStage.getTitle(), myResources.getString("EditorViewTitle"));
	}


	@Test
	void testExit() {
		testEditor();
		clickOn(lookup("#exit").query());
		assertEquals(myStage.getTitle(), myResources.getString("HomeViewTitle"));
	}
}
