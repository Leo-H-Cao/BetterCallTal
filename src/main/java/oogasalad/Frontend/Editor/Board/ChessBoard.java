package oogasalad.Frontend.Editor.Board;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.util.NodeContainer;
import java.util.concurrent.atomic.AtomicInteger;

public class ChessBoard extends NodeContainer {

	private GridPane myGrid;
	private SimpleIntegerProperty myWidth;
	private SimpleIntegerProperty myHeight;
	public ChessBoard(){
		myGrid = new GridPane();
		myHeight = getEditorBackend().getBoardState().getBoardHeight();
		myWidth = getEditorBackend().getBoardState().getBoardWidth();
	}

	@Override
	protected Node makeNode() {
		boolean alt = false;
		for(int i = 0; i < myWidth.get(); i++) {
			for(int j = 0; j < myHeight.get(); j++) {
				ChessBoardTile newTile = new ChessBoardTile(j, i, alt);
				myGrid.add(newTile.getNode(), j, i);
				alt = !alt;
			}
			alt = !alt;
		}
		myHeight.addListener((ob, ov, nv) -> updateGrid());
		myWidth.addListener((ob, ov, nv) -> updateGrid());
		return myGrid;
	}

	private void updateGrid() {
		LOG.debug("updating grid");
		myGrid = new GridPane();
		makeNode();
	}
}
