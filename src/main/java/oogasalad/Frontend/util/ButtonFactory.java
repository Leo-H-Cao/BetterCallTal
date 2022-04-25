package oogasalad.Frontend.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ButtonFactory {
	public static final String IMAGE_PATH = "src/main/resources/images/";

	private static final Logger LOG = LogManager.getLogger(ButtonFactory.class);

	public static Button makeButton(ButtonType type, String displayText, String id, EventHandler<ActionEvent> action) {
		Button b = new Button();
		b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		if (type == ButtonType.TEXT) {
			setText(b, displayText);
		} else if(type == ButtonType.IMAGE) {
			setImage(b, displayText);
		}
		b.setId(id);
		b.setOnAction(action);
		return b;
	}

	public static void addAction(Button b, EventHandler<ActionEvent> action) {
		b.setOnAction(action);
	}

	public static void addAction(Node n, EventHandler<MouseEvent> action) {
		n.setOnMouseClicked(action);
	}

	private static void setText(Button button, String display) {
		button.setText(display);
	}

	private static void setImage(Button button, String imageName) {
		try {
			Image image = new Image(new FileInputStream(IMAGE_PATH + imageName));
			ImageView imageView = new ImageView(image);
			button.setGraphic(imageView);
		} catch (FileNotFoundException ignored) {
			LOG.debug("Setting image failed");
		}
	}
}
