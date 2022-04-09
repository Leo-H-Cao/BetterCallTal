package oogasalad.frontend.editor.board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.editor.EditorBackend;
import oogasalad.frontend.util.NodeContainer;
import oogasalad.frontend.util.ButtonFactory;

public class ChessBoardTile extends NodeContainer {
	private final double myWidth;
	private final double myHeight;
	private final boolean alt;

	public ChessBoardTile(double w, double h, boolean toggled, EditorBackend controller) {
		super(controller);
		myWidth = w;
		myHeight = h;
		alt = toggled;
	}

	@Override
	protected Node makeNode() {
		return makeGridTile();
	}

	private Node makeGridTile() {
		Rectangle ret = new Rectangle(myWidth, myHeight);
		if (alt) {
			ret.setFill(Paint.valueOf("green"));
		} else {
			ret.setFill(Paint.valueOf("gray"));
		}
		ButtonFactory.addAction(ret, System.out::println);

		return ret;
	}
}
