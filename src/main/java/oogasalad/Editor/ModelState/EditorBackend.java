package oogasalad.Editor.ModelState;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.OPEN;

public class EditorBackend {
	private final PiecesState piecesState;
	private final BoardState boardState;
	private PieceGridTile currentlySelectedType;
	private Property<PieceGridTile> selectedTypeProperty;
	private Property<String> selectedPieceId;
	private SimpleIntegerProperty alternatePiece;

	public EditorBackend(){
		this.piecesState = new PiecesState();
		this.boardState = new BoardState(piecesState);
		currentlySelectedType = OPEN;
		selectedTypeProperty = new SimpleObjectProperty<>(currentlySelectedType);
		selectedPieceId = new SimpleStringProperty("rook");
		alternatePiece = new SimpleIntegerProperty(0);
		createDefaultPieces();
	}

	public PiecesState getPiecesState(){
		return piecesState;
	}

	public BoardState getBoardState(){
		return boardState;
	}

	public Property<PieceGridTile> getSelectedPieceEditorType() {
		return selectedTypeProperty;
	}

	public void setSelectedPieceEditorType(PieceGridTile selectedPieceEditorType) {
		selectedTypeProperty.setValue(selectedPieceEditorType);
	}

	public Property<String> getSelectedPieceId() {
		return selectedPieceId;
	}

	public void setSelectedPieceId(String id) {
		selectedPieceId.setValue(id);
	}

	public SimpleIntegerProperty getAlternatePiece() {
		return alternatePiece;
	}

	public void setAlternatePiece(int i) {
		alternatePiece.setValue(i);
	}

	private void createDefaultPieces() {
		createDefaultPiece("pawn", 1);
		createDefaultPiece("knight", 3);
		createDefaultPiece("bishop", 3);
		createDefaultPiece("rook", 5);
		createDefaultPiece("queen", 9);
		createDefaultPiece("king", 99);
	}

	private void createDefaultPiece(String name, int val) {
		getPiecesState().createCustomPiece(name);
		EditorPiece piece = getPiecesState().getPiece(name);
		piece.setImage(0, new Image("images/pieces/white/" + name + ".png"));
		piece.setImage(1, new Image("images/pieces/black/" + name + ".png"));
		piece.setPieceName(name);
		piece.setPointValue(val);
	}
}
