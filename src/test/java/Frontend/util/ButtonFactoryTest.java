package Frontend.util;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class ButtonFactoryTest extends ApplicationTest {
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

	void textButtonSimpleAction() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, "testButton", "testButton",
				(e) -> text.setText(newText));
		root.getChildren().add(b);
		clickOn(b);
		verifyThat(text.getText(), LabeledMatchers.hasText(newText));
	}
}
