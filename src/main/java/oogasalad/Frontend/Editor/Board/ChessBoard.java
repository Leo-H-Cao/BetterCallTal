package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.Editor.EditorBackend;
import oogasalad.Frontend.util.NodeContainer;
import java.util.concurrent.atomic.AtomicInteger;

public class ChessBoard extends NodeContainer {

	@Override
	protected Node makeNode() {
		return makeGrid();
	}

	private Node makeGrid() {
		GridPane grid = new GridPane();
		AtomicInteger width = new AtomicInteger(8);
		AtomicInteger height = new AtomicInteger(8);
		width.set(getEditorBackend().getBoardState().getBoardWidth());
		height.set(getEditorBackend().getBoardState().getBoardHeight());
		boolean alt = false;
		for(int i = 0; i < width.get(); i++) {
			for(int j = 0; j < height.get(); j++) {
				ChessBoardTile newTile = new ChessBoardTile(50, 50, alt);
				grid.add(newTile.getNode(), j, i);
				alt = !alt;
			}
			alt = !alt;
		}
		return grid;
	}
}
