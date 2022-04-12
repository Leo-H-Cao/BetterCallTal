package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.util.NodeContainer;

public class BoardEditor extends NodeContainer {
	private final ChessBoard myChessBoard;
	private final PieceLibrary myPieceLibrary;
	private final ModifierLibrary myModifierLibrary;

	public BoardEditor() {
		myChessBoard = new ChessBoard();
		myPieceLibrary = new PieceLibrary();
		myModifierLibrary = new ModifierLibrary();
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setPrefWidth(myScreenSize.getWidth());
		ret.setCenter(myChessBoard.getNode());
		ret.setLeft(myPieceLibrary.getNode());
		ret.setRight(myModifierLibrary.getNode());
		return ret;
	}
}
