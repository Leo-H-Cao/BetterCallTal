package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Editor.ModelState.BoardState;
import oogasalad.Frontend.NodeContainer;

public class ChessBoard extends NodeContainer {

	private final BoardState myBackend;

	public ChessBoard() {
		myBackend = new BoardState();
	}

	@Override
	protected Node makeNode() {
		return makeGrid();
	}

	private Node makeGrid() {
		GridPane grid = new GridPane();
		boolean alt = false;
		for(int i = 0; i < myBackend.getBoardWidth(); i++) {
			for(int j = 0; j < myBackend.getBoardHeight(); j++) {
				ChessBoardTile newTile = new ChessBoardTile(50, 50, alt);
				grid.add(newTile.getNode(), j, i);
				alt = !alt;
			}
			alt = !alt;
		}
		return grid;
	}
}
