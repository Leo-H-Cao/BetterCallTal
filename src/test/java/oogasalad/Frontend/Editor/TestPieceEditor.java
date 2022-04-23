package oogasalad.Frontend.Editor;

import javafx.stage.Stage;
import oogasalad.Frontend.util.BackendConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import java.util.ResourceBundle;

public class TestPieceEditor extends DukeApplicationTest {

	private Stage myStage;
	private EditorView myEditorView;
	private ResourceBundle myResources;

	@Override
	public void start (Stage stage) {
		myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
		myEditorView = new EditorView(stage);
		BackendConnector.initBackend(myResources);
	}

	@Test
	void testCreatePiece() {
		clickOn(lookup("#newCustomPiece").query());
		Assertions.assertNotNull(myEditorView.getMyTabs().getTabs().get(1));
	}
}
