package oogasalad.Frontend.editor.board;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.editor.EditorBackend;
import oogasalad.Frontend.util.NodeContainer;

import java.util.concurrent.atomic.AtomicInteger;

public class ChessBoard extends NodeContainer {

	public ChessBoard(EditorBackend controller) {
		super(controller);
	}

	@Override
	protected Node makeNode() {
		return makeGrid();
	}

	private Node makeGrid() {
		GridPane grid = new GridPane();
		AtomicInteger width = new AtomicInteger(8);
		AtomicInteger height = new AtomicInteger(8);
		getEditorBackend().ifPresent((editorController) -> {
			width.set(editorController.getBoardState().getBoardWidth());
			height.set(editorController.getBoardState().getBoardHeight());

			boolean alt = false;
			for(int i = 0; i < width.get(); i++) {
				for(int j = 0; j < height.get(); j++) {
					ChessBoardTile newTile = new ChessBoardTile(50, 50, alt, editorController);
					grid.add(newTile.getNode(), j, i);
					alt = !alt;
				}
				alt = !alt;
			}
		});
		return grid;
	}
}
