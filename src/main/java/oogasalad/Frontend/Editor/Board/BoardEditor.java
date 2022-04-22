package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.NodeContainer;

public class BoardEditor extends NodeContainer {
	private final ChessBoard myChessBoard;
	private final PieceLibrary myPieceLibrary;
	private final ModifierLibrary myModifierLibrary;
	private final BoardSettings myBoardSettings;

	public BoardEditor() {
		myChessBoard = new ChessBoard();
		myPieceLibrary = new PieceLibrary();
		myModifierLibrary = new ModifierLibrary();
		myBoardSettings = new BoardSettings();
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
		ret.setBottom(ButtonFactory.makeButton(ButtonType.TEXT, "Export", "export", e -> getEditorBackend().exportState()));
		return ret;
	}
}
