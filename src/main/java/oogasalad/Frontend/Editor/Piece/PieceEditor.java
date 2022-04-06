package oogasalad.Frontend.Editor.Piece;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Frontend.NodeContainer;

public class PieceEditor extends NodeContainer {
	private PiecesState myPiece;

	public PieceEditor() {

	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setPrefWidth(myScreenSize.getWidth());
		return ret;
	}
}
