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
		borderPane.setLeft(myPieceLibrary.getNode());
		borderPane.setRight(myModifierLibrary.getNode());

		AnchorPane ret = new AnchorPane(borderPane, myBoardSettings.getNode());
		AnchorPane.setTopAnchor(borderPane, 10.);
		AnchorPane.setBottomAnchor(myBoardSettings.getNode(), 10.);

		return ret;
	}
}
