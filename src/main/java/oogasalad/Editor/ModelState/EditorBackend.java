package oogasalad.Editor.ModelState;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import java.util.HashMap;
import java.util.Map;

public class EditorBackend {
	private final PiecesState piecesState;
	private final BoardState boardState;
	private final Map<String, EditorPiece> myEditorPieces;
	private PieceGridTile selectedPieceEditorType;

	public EditorBackend(){
		myEditorPieces = new HashMap<>();
		this.piecesState = new PiecesState();
		this.boardState = new BoardState(piecesState);
		selectedPieceEditorType = PieceGridTile.OPEN;
	}

	public PiecesState getPiecesState(){
		return piecesState;
	}

	public BoardState getBoardState(){
		return boardState;
	}

	public void createEditorPiece(String id) {
		myEditorPieces.put(id, new EditorPiece(id));
	}

	public EditorPiece getEditorPiece(String id) {
		return myEditorPieces.get(id);
	}

	public PieceGridTile getSelectedPieceEditorType() {
		return selectedPieceEditorType;
	}

	public void setSelectedPieceEditorType(PieceGridTile selectedPieceEditorType) {
		this.selectedPieceEditorType = selectedPieceEditorType;
	}
}
