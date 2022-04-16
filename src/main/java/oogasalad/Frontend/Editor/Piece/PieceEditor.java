package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.util.NodeContainer;

public class PieceEditor extends NodeContainer {
	private final String ID;
	private final PieceBoard myPieceBoard;
	private final PieceTileTypeSelector myTileTypeSelector;
	private final PieceSettings myPieceSettings;

	public PieceEditor(String id) {
		ID = id;
		myPieceBoard = new PieceBoard(id);
		myTileTypeSelector = new PieceTileTypeSelector(id);
		myPieceSettings = new PieceSettings(id);
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
		ret.setLeft(myTileTypeSelector.getNode());
		ret.setBottom(myPieceSettings.getNode());
		return ret;
	}
}
