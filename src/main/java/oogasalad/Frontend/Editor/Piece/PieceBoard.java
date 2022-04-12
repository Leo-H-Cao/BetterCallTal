package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Frontend.util.NodeContainer;

public class PieceBoard extends NodeContainer {
	@Override
	protected Node makeNode() {
		return makeBoard();
	}

	private Node makeBoard() {
		GridPane ret = new GridPane();
		ret.setVgap(5);
		ret.setHgap(5);
		int size = MovementGrid.PIECE_GRID_SIZE;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				PieceBoardTile newTile = new PieceBoardTile();
				ret.add(newTile.getNode(), j, i);
			}
		}
		return ret;
	}
}
