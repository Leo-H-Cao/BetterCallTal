package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
import java.util.ResourceBundle;

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
	protected Node fillContent() {
		Collection<Node> ret = new ArrayList<>();
		Arrays.stream(PieceGridTile.values()).filter((e) -> !e.equals(PieceGridTile.PIECE)).forEach((type) -> ret.add(makeTile(type)));
		FlowPane fp = new FlowPane();
		fp.getChildren().addAll(ret);
		return fp;
	}

	private Node makeTile(PieceGridTile type) {
		Rectangle rect;
		if(myResources.isPresent()) {
			ResourceBundle resources = myResources.get();
			double size = Double.parseDouble(resources.getString("Size"));
			int strokeWidth = Integer.parseInt(resources.getString("StrokeWidth"));
			rect = new Rectangle(size, size, Paint.valueOf(resources.getString("BackgroundColor")));
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

		} else {
			LOG.error("Properties File Missing!");
			rect = new Rectangle();
		}

		Label text = new Label(type.toString());
		StackPane ret = new StackPane(rect, text);
		ButtonFactory.addAction(ret, (e) -> getEditorBackend().setSelectedPieceEditorType(type));
		return ret;
	}
}
