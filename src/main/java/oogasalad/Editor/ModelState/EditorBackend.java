package oogasalad.Editor.ModelState;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import java.util.HashMap;
import java.util.Map;

public class EditorBackend {
	private final PiecesState myPiecesState;
	private final BoardState myBoardState;
	private final Map<String, EditorPiece> myEditorPieces;
	private PieceGridTile selectedPieceEditorType;

	public EditorBackend(){
		myEditorPieces = new HashMap<>();
		myPiecesState = new PiecesState();
		myBoardState = new BoardState(myPiecesState.getPiecesManager());
		selectedPieceEditorType = PieceGridTile.OPEN;
	}

	public PiecesState getPiecesState(){
		return myPiecesState;
	}

	public BoardState getBoardState(){
		return myBoardState;
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
