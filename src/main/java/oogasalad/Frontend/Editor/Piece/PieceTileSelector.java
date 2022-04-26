package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.LabelledContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

public class PieceTileSelector extends LabelledContainer {

	private ResourceBundle resources;

	public PieceTileSelector() {
		super(BackendConnector.getFrontendWord("PieceTileSelectorTitle"));
		myResources.ifPresent(e -> resources = e);
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	@Override
	protected Node fillContent() {
		Collection<Node> ret = new ArrayList<>();
		Arrays.stream(PieceGridTile.values()).filter((e) -> !e.equals(PieceGridTile.PIECE)).forEach((type) -> ret.add(makeTile(type)));
		FlowPane fp = new FlowPane();
		fp.getChildren().addAll(ret);
		return fp;
	}

	private Node makeTile(PieceGridTile type) {
		double size = Double.parseDouble(resources.getString("Size"));
		int strokeWidth = Integer.parseInt(resources.getString("StrokeWidth"));
		Rectangle rect = new Rectangle(size, size, type.getColor());
		rect.setStroke(Paint.valueOf(resources.getString("SelectionColor")));
		rect.setStrokeWidth(0);
		rect.setStrokeType(StrokeType.INSIDE);

		// Initially set stroke for selected type
		if(type == getEditorBackend().getSelectedPieceEditorType().getValue()) {
			rect.setStrokeWidth(strokeWidth);
		}

		// Listen for changes to the selected stroke type
		getEditorBackend().getSelectedPieceEditorType().addListener((ob, ov, nv) -> {
			// Reset other rectangles strokes
			rect.setStrokeWidth(0);
			if (nv == type) {
				rect.setStrokeWidth(strokeWidth);
			}
		});
		Label text = new Label(BackendConnector.getFrontendWord(type.toString(), getClass()));
		StackPane ret = new StackPane(rect, text);
		ButtonFactory.addAction(ret, (e) -> getEditorBackend().setSelectedPieceEditorType(type));
		return ret;
	}
}
