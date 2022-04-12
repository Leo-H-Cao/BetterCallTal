package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.NodeContainer;

public class ChessBoardTile extends NodeContainer {
	public static final int SIZE = 80;
	private final int myX;
	private final int myY;
	private final boolean alt;

	public ChessBoardTile(int x, int y, boolean toggled) {
		myX = x;
		myY = y;
		alt = toggled;
	}

	@Override
	protected Node makeNode() {
		return makeGridTile();
	}

	private Node makeGridTile() {
		Rectangle ret = new Rectangle(SIZE, SIZE);
		if (alt) {
			ret.setFill(Paint.valueOf("#bbb"));
		} else {
			ret.setFill(Paint.valueOf("gray"));
		}
		ButtonFactory.addAction(ret, (e) -> System.out.printf("X: %d, Y: %d\n", myX, myY));

		return ret;
	}
}
