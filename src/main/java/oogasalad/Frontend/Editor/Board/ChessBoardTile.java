package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.NodeContainer;
import oogasalad.Frontend.util.ButtonFactory;

public class ChessBoardTile extends NodeContainer {
	private double myWidth;
	private double myHeight;
	private boolean alt;
	private int x, y;

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
