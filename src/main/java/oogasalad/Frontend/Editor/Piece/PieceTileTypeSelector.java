package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import oogasalad.Frontend.Editor.EditorView;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.LabelledContainer;

import java.util.Collection;

public class PieceTileTypeSelector extends LabelledContainer {

	public PieceTileTypeSelector() {
		super(BackendConnector.getFrontendWord("PieceManager", EditorView.class));
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	@Override
	protected Collection<Node> fillContent() {
		return null;
	}
}
