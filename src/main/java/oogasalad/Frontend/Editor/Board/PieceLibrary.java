package oogasalad.Frontend.Editor.Board;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.LabelledContainer;
import java.util.ArrayList;
import java.util.Collection;

public class PieceLibrary extends LabelledContainer {

	public PieceLibrary() {
		super("Piece Library");
		createDefaultPieces();
	}

	@Override
	protected Node fillContent() {
		Collection<Node> ret = new ArrayList();
		getEditorBackend().getPiecesState().getAllPieces().forEach((piece) -> ret.add(createPiece(piece.getPieceID())));
		FlowPane fp = new FlowPane();
		fp.getChildren().addAll(ret);
		return fp;
	}

	private Node createPiece(String id) {
		int size = 80;
		Rectangle rect = new Rectangle(size, size);
		rect.setFill(Paint.valueOf("#ccc"));
		rect.setStroke(Paint.valueOf("blue"));
		rect.setStrokeWidth(2);
		rect.setStrokeType(StrokeType.INSIDE);
		ImageView image = new ImageView(getEditorBackend().getPiecesState().getPiece(id).getImage(0).getValue());
		image.setFitHeight(size - 5);
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		StackPane ret = new StackPane(rect, image);
		ButtonFactory.addAction(ret, (e) -> LOG.debug("clicked " + id));
		return new Group(ret);
	}

	private void createDefaultPieces() {
		// Rook
		MovementGrid rookMovement = new MovementGrid();
		getEditorBackend().getPiecesState().createCustomPiece("rook");
	}
}
