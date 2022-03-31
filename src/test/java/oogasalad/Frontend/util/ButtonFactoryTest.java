package oogasalad.Frontend.util;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static oogasalad.Frontend.util.ButtonType.TEXT;

public class ButtonFactoryTest extends DukeApplicationTest {
	private String oldText = "old";
	private String newText = "new";
	private Text text;
	private Group root;

	@Override
	public void start(Stage stage) {
		root = new Group();
		text = new Text(oldText);
		root.getChildren().add(text);
		Scene scene = new Scene(root, 100, 100);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	void textButtonSimpleAction() {
		Button b = ButtonFactory.makeButton(TEXT, "testButton", "testButton",
				(e) -> text.setText(newText));
		runAsJFXAction(() -> root.getChildren().add(b));
		clickOn(b);
		Assertions.assertEquals(text.getText(), newText);
	}
}
