package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.NodeContainer;

public class PieceLibrary extends NodeContainer {

	@Override
	protected Node makeNode() {
		Rectangle r = new Rectangle(100, 600);
		r.setFill(Paint.valueOf("red"));
		return r;
	}
}
