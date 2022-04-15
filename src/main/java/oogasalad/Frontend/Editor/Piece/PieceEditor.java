package oogasalad.Frontend.Editor.Piece;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.util.NodeContainer;

public class PieceEditor extends NodeContainer {
	private final String ID;
	private final PieceBoard myPieceBoard;
	private final PieceTileTypeSelector myTileTypeSelector;

	public PieceEditor(String id) {
		ID = id;
		myPieceBoard = new PieceBoard(id);
		myTileTypeSelector = new PieceTileTypeSelector(id);

		Property<PieceGridTile> selectedPiece = getEditorBackend().getSelectedPieceEditorType();

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
		return ret;
	}
}
