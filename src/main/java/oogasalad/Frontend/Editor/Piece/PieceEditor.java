package oogasalad.Frontend.Editor.Piece;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.util.NodeContainer;

public class PieceEditor extends NodeContainer {
	private final String ID;
	private final PieceBoard myPieceBoard;
	private final PieceTileTypeSelector myTileTypeSelector;
	private final PieceSettings myPieceSettings;
	private SimpleBooleanProperty alt;

	public PieceEditor(String id) {
		ID = id;
		myPieceBoard = new PieceBoard(id);
		myTileTypeSelector = new PieceTileTypeSelector(id);
		myPieceSettings = new PieceSettings(id);
		alt = new SimpleBooleanProperty(false);
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
		ret.setRight(myPieceSettings.getNode());
		return ret;
	}
}
