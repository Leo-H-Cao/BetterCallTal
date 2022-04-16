package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
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
		Button setBlackPieceImage = ButtonFactory.makeButton(ButtonType.TEXT, "Change main image", "changeMainImage", (e) -> choosePiece(0));
		Button setWhitePieceImage = ButtonFactory.makeButton(ButtonType.TEXT, "Change alternate image", "changeAltImage", (e) -> choosePiece(1));
		FlowPane ret = new FlowPane();
		ret.getChildren().addAll(setBlackPieceImage, setWhitePieceImage);
		return ret;
	}

	private void choosePiece(int team) {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open piece image");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		getEditorBackend().getPiecesState().getPiece(myId).setImage(team, new Image(selectedFile.getAbsolutePath()));
	}
}
