package oogasalad.Frontend.util;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.awt.*;

import static oogasalad.Frontend.util.ButtonType.TEXT;

public class ButtonFactoryTest extends DukeApplicationTest {
	private String oldText = "old";
	private String newText = "new";
	private Text text;
	private Group root;
	private Stage myStage;

	@Override
	public void start(Stage stage) {
		root = new Group();
		text = new Text(oldText);
		root.getChildren().add(text);
		myStage = stage;
		Scene scene = new Scene(root, 100, 100);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	void testButtonSimpleAction() {
		Button b = ButtonFactory.makeButton(TEXT, "testButton", "testButton",
				(e) -> text.setText(newText));
		runAsJFXAction(() -> root.getChildren().add(b));
		clickOn(b);
		Assertions.assertEquals(text.getText(), newText);
	}

	@Test
	void testAddActionSimple() {
		FlowPane pane = new FlowPane();
		String rectText = "rect";
		Circle c = new Circle(20, 50,25);
		ButtonFactory.addAction(c, (e) -> myStage.setTitle(newText));
		Rectangle r = new Rectangle(20, 50, Paint.valueOf("red"));
		ButtonFactory.addAction(r, (e) -> myStage.setTitle(rectText));
		runAsJFXAction(() -> {
			pane.getChildren().addAll(c, r);
			root.getChildren().add(pane);
		});
		clickOn(c);
		Assertions.assertEquals(myStage.getTitle(), newText);
		clickOn(r);
		Assertions.assertEquals(myStage.getTitle(), rectText);
	}
}
