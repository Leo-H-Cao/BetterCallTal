package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Editor.ModelState.BoardState;

public class ChessBoard {

	private GridPane myGrid;
	private final BoardState myBackend;

	public ChessBoard() {
		myBackend = new BoardState();
	}

	public Node getNode() {
		if(myGrid == null) {
			makeGrid();
		}
		return myGrid;
	}

	private void makeGrid() {
		myGrid = new GridPane();
		boolean alt = false;
		for(int i = 0; i < myBackend.getBoardWidth(); i++) {
			for(int j = 0; j < myBackend.getBoardHeight(); j++) {
				ChessBoardTile newTile = new ChessBoardTile(25, 25, alt);
				myGrid.add(newTile.getNode(), j, i);
				alt = !alt;
			}
			alt = !alt;
		}
	}
}
