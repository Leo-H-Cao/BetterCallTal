package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.NodeContainer;

public class PieceEditor extends NodeContainer {

	@Override
	protected Node makeNode() {
		return new Rectangle(50, 50);
	}
}
