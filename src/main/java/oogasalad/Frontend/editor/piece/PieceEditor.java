package oogasalad.Frontend.editor.piece;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.editor.EditorBackend;
import oogasalad.Frontend.util.NodeContainer;

public class PieceEditor extends NodeContainer {
	private final String ID;

	public PieceEditor(EditorBackend controller, String id) {
		super(controller);
		ID = id;
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
		return ret;
	}
}
