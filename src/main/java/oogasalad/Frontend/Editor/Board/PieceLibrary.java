package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.LabelledContainer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class PieceLibrary extends LabelledContainer {

	private ResourceBundle resources;

	public PieceLibrary() {
		super(BackendConnector.getFrontendWord("PieceLibraryTitle"));
		myResources.ifPresent(e -> resources = e);
	}

	@Override
	protected Node fillContent() {
		GridPane ret = new GridPane();
		Collection<Node> listOfNodes = new ArrayList();
		getEditorBackend().getPiecesState().getAllPieces().getValue().forEach((piece) -> listOfNodes.add(createPiece(piece.getPieceID())));
		FlowPane fp = new FlowPane();
		fp.getChildren().addAll(listOfNodes);

		// Listen for updates to the piece library
		getEditorBackend().getPiecesState().getAllPieces().addListener((ob, ov, nv) -> {
			listOfNodes.clear();
			fp.getChildren().clear();
			nv.forEach((piece) -> listOfNodes.add(createPiece(piece.getPieceID())));
			fp.getChildren().addAll(listOfNodes);
		});

		CheckBox placeAlternateColor = new CheckBox(BackendConnector.getFrontendWord("Alt", getClass()));
		placeAlternateColor.selectedProperty().addListener((ob, ov, nv) -> {
			if(nv) {
				getEditorBackend().setAlternatePiece(1);
			} else {
				getEditorBackend().setAlternatePiece(0);
			}
		});

		Label instructions = new Label(BackendConnector.getFrontendWord("Instructions", getClass()));

		ret.add(instructions, 0, 0);
		ret.add(placeAlternateColor, 0, 1);
		ret.add(fp, 0, 2);

		return ret;
	}

	private Node createPiece(String id) {
		double size = Double.parseDouble(resources.getString("TileSize"));
		double strokeWidth = Double.parseDouble(resources.getString("StrokeWidth"));
		Rectangle rect = new Rectangle(size, size);
		rect.setFill(Paint.valueOf(resources.getString("FillColor")));
		rect.setStroke(Paint.valueOf(resources.getString("StrokeColor")));
		rect.setStrokeWidth(0);
		rect.setStrokeType(StrokeType.INSIDE);

		EditorPiece piece = getEditorBackend().getPiecesState().getPiece(id);

		// Set initial outline
		if(id.equals(getEditorBackend().getSelectedPieceId().getValue())) {
			rect.setStrokeWidth(strokeWidth);
		}

		getEditorBackend().getSelectedPieceId().addListener((e) -> {
			if(id.equals(getEditorBackend().getSelectedPieceId().getValue())) {
				rect.setStrokeWidth(strokeWidth);
			} else {
				rect.setStrokeWidth(0);
			}
		});

		ImageView image = new ImageView(piece.getImage(getEditorBackend().getSelectedTeam().getValue()).getValue());
		image.setFitHeight(size - PADDING);
		image.setFitWidth(size - PADDING);
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);

		// Listen for changes to the custom piece images
		addPieceImageListener(piece, 0, image);
		addPieceImageListener(piece, 1, image);

		// Listen for changing to alternate pieces
		getEditorBackend().getSelectedTeam().addListener((ob, ov, nv) -> image.setImage(piece.getImage((Integer) nv).getValue()));
		StackPane ret = new StackPane(rect, image);
		ret.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				getEditorBackend().setSelectedPieceId(id);
			} else if(e.getButton() == MouseButton.SECONDARY) {
				getEditorBackend().setOpenCustomPieceProperty(id);
			}
		});
		ret.setId(id);
		return ret;
	}

	private void addPieceImageListener(EditorPiece p, int team, ImageView image) {
		p.getImage(team).addListener((ob, ov, nv) -> {
			if(getEditorBackend().getSelectedTeam().getValue() == team) {
				image.setImage(nv);
			}
		});
	}
}
