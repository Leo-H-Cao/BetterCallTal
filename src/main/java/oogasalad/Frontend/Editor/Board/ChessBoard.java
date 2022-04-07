package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.Editor.NodeContainer;

public class ChessBoard extends NodeContainer {

	public ChessBoard(EditorController controller) {
		super(controller);
	}

	@Override
	protected Node makeNode() {
		return makeGrid();
	}

	private Node makeGrid() {
		GridPane grid = new GridPane();
		boolean alt = false;
		for(int i = 0; i < myController.getBoardState().getBoardWidth(); i++) {
			for(int j = 0; j < myController.getBoardState().getBoardHeight(); j++) {
				ChessBoardTile newTile = new ChessBoardTile(50, 50, alt, myController);
				grid.add(newTile.getNode(), j, i);
				alt = !alt;
			}
			alt = !alt;
		}
		return grid;
	}
}
