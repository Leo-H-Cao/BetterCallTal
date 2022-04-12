package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.NodeContainer;

public class ChessBoardTile extends NodeContainer {
	private final double myWidth;
	private final double myHeight;
	private final boolean alt;

	public ChessBoardTile(double w, double h, boolean toggled) {
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
