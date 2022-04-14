package oogasalad.Frontend.Editor.Piece;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.Editor.EditorView;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.LabelledContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PieceTileTypeSelector extends LabelledContainer {

	private String myId;

	public PieceTileTypeSelector(String id) {
		super(BackendConnector.getFrontendWord("TileType", EditorView.class));
		myId = id;
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	@Override
	protected Collection<Node> fillContent() {
		Collection<Node> ret = new ArrayList<>();
		Arrays.stream(PieceGridTile.values()).forEach((type) -> ret.add(makeTile(type)));
		return ret;
	}

	private Node makeTile(PieceGridTile type) {
		Rectangle rect = new Rectangle(50, 50, Paint.valueOf("lightblue"));
		rect.setStroke(Paint.valueOf("black"));
		rect.setStrokeWidth(0);
		rect.setStrokeType(StrokeType.INSIDE);

		// Initially set
		if(type == getEditorBackend().getSelectedPieceEditorType().getValue()) {
			rect.setStrokeWidth(3);
		}

		Property<PieceGridTile> p = getEditorBackend().getSelectedPieceEditorType();
		p.addListener((ob, ov, nv) -> {
			// Reset other rectangles strokes
			rect.setStrokeWidth(0);
			if (nv == type) {
				rect.setStrokeWidth(3);
			}
		});

		Label text = new Label(type.toString());
		StackPane ret = new StackPane(rect, text);
		ButtonFactory.addAction(ret, (e) -> getEditorBackend().setSelectedPieceEditorType(type));
		return ret;
	}
}
