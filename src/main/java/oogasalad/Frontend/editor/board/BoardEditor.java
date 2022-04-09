package oogasalad.Frontend.editor.board;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.editor.EditorBackend;
import oogasalad.Frontend.util.NodeContainer;
import oogasalad.Frontend.util.Controller;

public class BoardEditor extends NodeContainer {
	private final ChessBoard myChessBoard;
	private final PieceLibrary myPieceLibrary;
	private final ModifierLibrary myModifierLibrary;
	private Controller myBackend;

	public BoardEditor(Controller controller) {
		super(controller);
		getEditorBackend().ifPresent((e) -> myBackend = e);
		myChessBoard = new ChessBoard((EditorBackend) myBackend);
		myPieceLibrary = new PieceLibrary((EditorBackend) myBackend);
		myModifierLibrary = new ModifierLibrary((EditorBackend) myBackend);
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
