package oogasalad.Frontend.Editor.Board;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Frontend.util.ButtonFactory;
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

		ImageView image = new ImageView(piece.getImage(0).getValue());
		image.setFitHeight(size - 5);
		image.setFitWidth(size - 5);
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);

		// Listen for changes to the custom piece images
		piece.getImage(0).addListener((ob, ov, nv) -> image.setImage(nv));
		piece.getImage(1).addListener((ob, ov, nv) -> image.setImage(nv));

		// Listen for changing to alternate pieces
		getEditorBackend().getAlternatePiece().addListener((ob, ov, nv) -> image.setImage(piece.getImage((Integer) nv).getValue()));
		StackPane ret = new StackPane(rect, image);
		ButtonFactory.addAction(ret, (e) -> getEditorBackend().setSelectedPieceId(id));
		return new Group(ret);
	}
}
