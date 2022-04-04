package oogasalad.Frontend.Editor;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;

public class ChessBoardView {
	private GridPane myGrid;
	private int myWidth;
	private int myHeight;

	public ChessBoardView() {
		myWidth = 10;
		myHeight = 10;
		makeGrid();
	}

	public Node getNode() {
		Group ret = new Group();
		ret.getChildren().add(myGrid);

		return ret;
	}

	private void makeGrid() {
		myGrid = new GridPane();

		for(int i = 0; i < myWidth; i++) {
			for(int j = 0; j < myHeight; j++) {
				myGrid.add(makeGridTile(25, 25), i, j);
			}
		}
	}

	private Node makeGridTile(double width, double height) {
		Rectangle ret = new Rectangle(width, height);
//		ret.setFill(Paint.valueOf("green"));
		ButtonFactory.addAction(ret, (e) -> System.out.println("clicked"));

		return ret;
	}
}
