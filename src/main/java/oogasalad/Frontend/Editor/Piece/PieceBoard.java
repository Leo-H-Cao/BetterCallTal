package oogasalad.Frontend.Editor.Piece;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Frontend.util.NodeContainer;

public class PieceBoard extends NodeContainer {
	private final String myId;
	private final int mySelectedTeam;

	public PieceBoard(String id, int selectedTeam) {
		myId = id;
		mySelectedTeam = selectedTeam;
	}

	@Override
	protected Node makeNode() {
		GridPane ret = new GridPane();
		ret.add(makeName(), 0, 0);
		ret.add(makeBoard(), 0, 1);
		return ret;
	}

	private Node makeBoard() {
		GridPane ret = new GridPane();
		ret.setVgap(5);
		ret.setHgap(5);
		int size = MovementGrid.PIECE_GRID_SIZE;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				PieceBoardTile newTile = new PieceBoardTile(j, i, getEditorBackend().getPiecesState().getPiece(myId).getTileStatus(j, i, mySelectedTeam), myId, mySelectedTeam);
				ret.add(newTile.getNode(), j, i);
			}
		}
		return ret;
	}

	private Node makeName() {
		TextField textArea = new TextField(getEditorBackend().getPiecesState().getPiece(myId).getPieceName().getValue());
		textArea.setFont(new Font(36));
		textArea.setId("pieceName");
		textArea.setAlignment(Pos.CENTER);

		textArea.textProperty().addListener((ob, ov, nv) -> getEditorBackend().getPiecesState().getPiece(myId).setPieceName(nv));

		return textArea;
	}
}
