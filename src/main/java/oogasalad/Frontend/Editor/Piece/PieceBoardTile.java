package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.util.NodeContainer;

public class PieceBoardTile extends NodeContainer {
	public static int SIZE = 80;
	private PieceGridTile status;


	@Override
	protected Node makeNode() {
		return makeTile();
	}

	private Node makeTile() {
		Rectangle rect = new Rectangle(SIZE, SIZE, Paint.valueOf("#bbb"));
		Group ret = new Group(rect);

		return ret;
	}
}
