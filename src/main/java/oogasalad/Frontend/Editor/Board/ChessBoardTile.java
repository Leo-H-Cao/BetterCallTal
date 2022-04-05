package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;

public class ChessBoardTile {
	private Node myTile;
	private double myWidth;
	private double myHeight;
	private boolean alt;

	public ChessBoardTile(double w, double h, boolean toggled) {
		myWidth = w;
		myHeight = h;
		alt = toggled;
	}

	public Node getNode() {
		if(myTile == null) {
			myTile = makeGridTile();
		}
		return myTile;
	}

	private Node makeGridTile() {
		Rectangle ret = new Rectangle(myWidth, myHeight);
		if (alt) {
			ret.setFill(Paint.valueOf("green"));
		} else {
			ret.setFill(Paint.valueOf("gray"));
		}
		ButtonFactory.addAction(ret, (e) -> System.out.println(e));

		return ret;
	}
}
