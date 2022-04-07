package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.Editor.NodeContainer;

public class BoardEditor extends NodeContainer {
	private final ChessBoard myChessBoard;
	private final PieceLibrary myPieceLibrary;
	private final ModifierLibrary myModifierLibrary;

	public BoardEditor(EditorController controller) {
		super(controller);
		myChessBoard = new ChessBoard(myController);
		myPieceLibrary = new PieceLibrary(myController);
		myModifierLibrary = new ModifierLibrary(myController);
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
