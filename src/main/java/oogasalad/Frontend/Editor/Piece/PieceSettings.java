package oogasalad.Frontend.Editor.Piece;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.LabelledContainer;
import java.io.File;

public class PieceSettings extends LabelledContainer {
	private final String myId;
	private final SimpleIntegerProperty mySelectedTeam;

	public PieceSettings(String id, SimpleIntegerProperty selectedTeam) {
		super("Piece Settings");
		myId = id;
		mySelectedTeam = selectedTeam;
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	@Override
	protected Node fillContent() {
		FlowPane ret = new FlowPane();
		ret.setPrefWrapLength(1);
		ret.setVgap(GAP);
		Button setPieceImage = ButtonFactory.makeButton(ButtonType.TEXT, "Change image", "changeImage", (e) -> choosePieceImage());
		ret.getChildren().add(makeTeamToggle());

		// Prevent changing the image of a read only piece
		if(!getEditorBackend().getPiecesState().getPiece(myId).isDefaultPiece()) {
			ret.getChildren().add(setPieceImage);
		}

		return ret;
	}

	private Node makeTeamToggle() {
		GridPane ret = new GridPane();
		ToggleGroup tg = new ToggleGroup();

		RadioButton mainPieceSelector = new RadioButton("Main Piece");
		mainPieceSelector.setId("selectMain");

		RadioButton altPieceSelector = new RadioButton("Alternate Piece");
		altPieceSelector.setId("selectAlt");
		tg.getToggles().addAll(mainPieceSelector, altPieceSelector);

		tg.selectedToggleProperty().addListener((ob, ov, nv) -> {
			for(int i = 0; i < tg.getToggles().size(); i++) {
				if(tg.getToggles().get(i).equals(nv)) {
					mySelectedTeam.setValue(i);
				}
			}
		});

		mainPieceSelector.fire();
		ret.add(mainPieceSelector, 0, 0);
		ret.add(altPieceSelector, 1,0);
		ret.setHgap(GAP);

		return ret;
	}

	private void choosePieceImage() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open piece image");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		if(selectedFile != null) {
			getEditorBackend().getPiecesState().getPiece(myId).setImage(mySelectedTeam.getValue(), new Image(selectedFile.getAbsolutePath()));
		}
	}
}
