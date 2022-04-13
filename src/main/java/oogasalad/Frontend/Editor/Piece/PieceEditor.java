package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.util.NodeContainer;

public class PieceEditor extends NodeContainer {
	private final String ID;
	private final PieceBoard myPieceBoard;
	private final PieceTileTypeSelector myTileTypeSelector;

	public PieceEditor(String id) {
		ID = id;
		myPieceBoard = new PieceBoard(id);
		myTileTypeSelector = new PieceTileTypeSelector(id);
	}

	public String getId() {
		return ID;
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setPrefWidth(myScreenSize.getWidth());
		ret.setCenter(myPieceBoard.getNode());
		return ret;
	}
}
