package oogasalad.Frontend.Editor;

import javafx.scene.Node;
import javafx.scene.control.Tab;
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
		myStage = stage;
		myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
		BackendConnector.initBackend(myResources);
		myEditorView = new EditorView(stage);
		stage.setScene(myEditorView.getScene());
		stage.show();
	}

	@Test
	void createPiece() {
		Assertions.assertEquals(1, myEditorView.getMyTabs().getTabs().size());
		clickOn(lookup("#newCustomPiece").query());
		Assertions.assertEquals(2, myEditorView.getMyTabs().getTabs().size());
	}

	@Test
	void openPieceEditor() {
		createPiece();
		myEditorView.getMyTabs().getSelectionModel().select(0);
		String newCustomPieceId = "#custom1";
		rightClickOn((Node)lookup(newCustomPieceId).query());
		Assertions.assertEquals(myEditorView.getMyTabs().getTabs().get(1), myEditorView.getMyTabs().getSelectionModel().getSelectedItem());
	}

	//@Test
	void reopenPieceEditor() {
		createPiece();
		myEditorView.getMyTabs().getSelectionModel().select(0);
		String newCustomPieceId = "#custom1";

		Tab newTab = myEditorView.getMyTabs().getTabs().get(1);

		sleep(50);
		runAsJFXAction(() -> myEditorView.getMyTabs().getTabs().remove(newTab));

		sleep(1000);

		rightClickOn((Node)lookup(newCustomPieceId).query());
		sleep(250);
		Assertions.assertEquals(myEditorView.getMyTabs().getTabs().get(1), myEditorView.getMyTabs().getSelectionModel().getSelectedItem());

	}
}
