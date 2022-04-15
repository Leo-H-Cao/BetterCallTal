package oogasalad.Editor.ModelState;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import java.util.HashMap;
import java.util.Map;

import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.OPEN;

public class EditorBackend {
	private final PiecesState piecesState;
	private final BoardState boardState;
	private final Map<String, EditorPiece> myEditorPieces;
	private PieceGridTile currentlySelectedType;
	private Property<PieceGridTile> selectedTypeProperty;

	public EditorBackend(){
		myEditorPieces = new HashMap<>();
		this.piecesState = new PiecesState();
		this.boardState = new BoardState(piecesState);
		currentlySelectedType = OPEN;
		selectedTypeProperty = new SimpleObjectProperty<>(currentlySelectedType);
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

	public Property<PieceGridTile> getSelectedPieceEditorType() {
		return selectedTypeProperty;
	}

	public void setSelectedPieceEditorType(PieceGridTile selectedPieceEditorType) {
		selectedTypeProperty.setValue(selectedPieceEditorType);
	}
}
