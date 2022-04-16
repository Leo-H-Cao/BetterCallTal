package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.LabelledContainer;

import java.io.File;

public class PieceSettings extends LabelledContainer {
	private String myId;

	public PieceSettings(String id) {
		super("Piece Settings");
		myId = id;
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	@Override
	protected Node fillContent() {
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, "Change black image", "changeBlackImage", (e) -> {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open black piece image");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
			File selectedFile = fileChooser.showOpenDialog(stage);
			getEditorBackend().getEditorPiece(myId).setImage(0, new Image(selectedFile.getAbsolutePath()));
		});
		return b;
	}
}
