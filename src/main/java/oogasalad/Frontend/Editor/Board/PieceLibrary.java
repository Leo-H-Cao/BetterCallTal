package oogasalad.Frontend.Editor.Board;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Frontend.util.LabelledContainer;
import java.util.ArrayList;
import java.util.Collection;

public class PieceLibrary extends LabelledContainer {

	public PieceLibrary() {
		super("Piece Library");
	}

	@Override
	protected Node fillContent() {
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
		return fp;
	}

	private Node createPiece(String id) {
		int size = 80;
		Rectangle rect = new Rectangle(size, size);
		rect.setFill(Paint.valueOf("#ccc"));
		rect.setStroke(Paint.valueOf("blue"));
		rect.setStrokeWidth(0);
		rect.setStrokeType(StrokeType.INSIDE);

		EditorPiece piece = getEditorBackend().getPiecesState().getPiece(id);

		// Set initial outline
		if(id.equals(getEditorBackend().getSelectedPieceId().getValue())) {
			rect.setStrokeWidth(2);
		}

		getEditorBackend().getSelectedPieceId().addListener((e) -> {
			if(id.equals(getEditorBackend().getSelectedPieceId().getValue())) {
				rect.setStrokeWidth(2);
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
		return new Group(ret);
	}

	private void addPieceImageListener(EditorPiece p, int team, ImageView image) {
		p.getImage(team).addListener((ob, ov, nv) -> {
			if(getEditorBackend().getSelectedTeam().getValue() == team) {
				image.setImage(nv);
			}
		});
	}
}
