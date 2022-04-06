package oogasalad.Frontend.Editor.Board;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.Editor.LabelledContainer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PieceLibrary extends LabelledContainer {


	public PieceLibrary() {
		super("Piece Library");
	}

	@Override
	protected Collection<Node> fillContent() {
		List ret = new ArrayList();
		for (int i = 0; i < 30; i++) {
			ret.add(createPiece());
		}
		return ret;
	}

	private Node createPiece() {
		Rectangle rect = new Rectangle(80, 80);
		rect.setFill(Paint.valueOf("blue"));
		StackPane ret = new StackPane(rect);
		return new Group(ret);
	}
}
