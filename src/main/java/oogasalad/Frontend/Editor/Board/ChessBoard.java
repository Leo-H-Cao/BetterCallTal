package oogasalad.Frontend.Editor.Board;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Editor.ModelState.BoardState.EditorBoard;
import oogasalad.Frontend.util.NodeContainer;

public class ChessBoard extends NodeContainer {
	private GridPane myGrid;
	private final SimpleIntegerProperty myWidth;
	private final SimpleIntegerProperty myHeight;
	public ChessBoard(){
		myGrid = new GridPane();
		myHeight = getEditorBackend().getBoardState().getHeight();
		myWidth = getEditorBackend().getBoardState().getWidth();

		myHeight.addListener((ob, ov, nv) -> updateGrid());
		myWidth.addListener((ob, ov, nv) -> updateGrid());
	}

	@Override
	protected Node makeNode() {
		boolean alt = false;
		double xSizeMultiplier = EditorBoard.DEFAULT_BOARD_SIZE/Double.valueOf(myWidth.getValue());
		double ySizeMultiplier = EditorBoard.DEFAULT_BOARD_SIZE/Double.valueOf(myHeight.getValue());
		for(int i = 0; i < myHeight.getValue(); i++) {
			for(int j = 0; j < myWidth.getValue(); j++) {
				ChessBoardTile newTile = new ChessBoardTile(j, i, alt, xSizeMultiplier, ySizeMultiplier);
				myGrid.add(newTile.getNode(), j, i);
				alt = !alt;
			}
			if(myWidth.getValue() % 2 == 0) {
				alt = !alt;
			}
		}

		return myGrid;
	}

	private void updateGrid() {
		myGrid.getChildren().clear();
		myGrid = (GridPane) makeNode();
	}
}
