package oogasalad.Frontend.Editor.Board;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.util.LabelledContainer;
import oogasalad.Frontend.util.ButtonFactory;
import java.util.ArrayList;
import java.util.Collection;

public class PieceLibrary extends LabelledContainer {

	public PieceLibrary(EditorController controller) {
		super("Piece Library", controller);
	}

	@Override
	protected Collection<Node> fillContent() {
		Collection<Node> ret = new ArrayList();
		getBackend(EditorController.class).ifPresent((e) ->
				e.getPiecesState().getAllPieces().forEach((piece) -> ret.add(createPiece(piece.getPieceID()))));
		return ret;
	}

	private Node createPiece(String id) {
		Rectangle rect = new Rectangle(80, 80);
		rect.setFill(Paint.valueOf("white"));
		rect.setStroke(Paint.valueOf("blue"));
		rect.setStrokeWidth(3);
		rect.setStrokeType(StrokeType.INSIDE);
		StackPane ret = new StackPane(rect);
		ButtonFactory.addAction(ret, (e) -> LOG.debug("clicked " + id));
		return new Group(ret);
	}
}
