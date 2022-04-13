package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import oogasalad.Frontend.Editor.EditorView;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.LabelledContainer;

import java.util.ArrayList;
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
//		for()

		return ret;
	}

}
