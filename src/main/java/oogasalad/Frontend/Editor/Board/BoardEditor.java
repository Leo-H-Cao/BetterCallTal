package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
		BorderPane borderPane = new BorderPane();
		borderPane.setPrefWidth(myScreenSize.getWidth());
		borderPane.setCenter(myChessBoard.getNode());
		borderPane.setLeft(makeLeft());
		borderPane.setRight(myModifierLibrary.getNode());

		return borderPane;
	}

	private Node makeLeft() {
		GridPane ret = new GridPane();
		ret.add(myPieceLibrary.getNode(), 0, 0);
		ret.add(myBoardSettings.getNode(), 0, 1);
		return ret;
	}
}
