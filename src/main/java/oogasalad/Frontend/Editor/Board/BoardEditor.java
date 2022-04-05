package oogasalad.Frontend.Editor.Board;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class BoardEditor {
	private ChessBoard myChessBoard;
	private Group myNode;

	public BoardEditor() {
		myChessBoard = new ChessBoard();
	}

	public Node getNode() {
		if(myNode == null) {
			myNode = new Group();
			myNode.getChildren().add(makeLayout());
		}
		return myNode;
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setCenter(myChessBoard.getNode());
		return ret;
	}
}
