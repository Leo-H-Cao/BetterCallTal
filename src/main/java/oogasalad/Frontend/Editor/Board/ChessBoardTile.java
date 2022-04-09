package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.util.NodeContainer;
import oogasalad.Frontend.util.ButtonFactory;

public class ChessBoardTile extends NodeContainer {
	private final double myWidth;
	private final double myHeight;
	private final boolean alt;

	public ChessBoardTile(double w, double h, boolean toggled, EditorController controller) {
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
