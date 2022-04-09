package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.Editor.NodeContainer;
import oogasalad.Frontend.util.Controller;

public class BoardEditor extends NodeContainer {
	private final ChessBoard myChessBoard;
	private final PieceLibrary myPieceLibrary;
	private final ModifierLibrary myModifierLibrary;
	private Controller myBackend;

	public BoardEditor(Controller controller) {
		super(controller);
		getBackend(EditorController.class).ifPresent((e) -> myBackend = e);
		myChessBoard = new ChessBoard((EditorController) myBackend);
		myPieceLibrary = new PieceLibrary((EditorController) myBackend);
		myModifierLibrary = new ModifierLibrary((EditorController) myBackend);
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
