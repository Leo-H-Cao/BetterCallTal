package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
		Rectangle rect = new Rectangle(50, 50, Paint.valueOf("red"));
		Label text = new Label(type.toString());
		StackPane ret = new StackPane(rect, text);
		ButtonFactory.addAction(ret, (e) -> {
			getEditorBackend().setSelectedPieceEditorType(type);
			System.out.println(getEditorBackend().getSelectedPieceEditorType());
		});
		return ret;
	}
}
