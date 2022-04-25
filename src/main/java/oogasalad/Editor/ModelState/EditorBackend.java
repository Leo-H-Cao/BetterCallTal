package oogasalad.Editor.ModelState;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import oogasalad.Editor.ExportJSON.ExportJSON;
import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Editor.ModelState.EditPiece.MovementGrid;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Editor.ModelState.RulesState.GameRulesState;

import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.OPEN;

public class EditorBackend {
	private final PiecesState piecesState;
	private final BoardState boardState;
	private final GameRulesState gameRulesState;
	private final Property<PieceGridTile> selectedTypeProperty;
	private final SimpleStringProperty selectedPieceId;
	private final SimpleIntegerProperty alternatePiece;
	private final SimpleStringProperty customPieceOpenId;

	public EditorBackend(){
		piecesState = new PiecesState();
		boardState = new BoardState(piecesState);
		gameRulesState = new GameRulesState();
		selectedTypeProperty = new SimpleObjectProperty<>(OPEN);
		selectedPieceId = new SimpleStringProperty("rook");
		alternatePiece = new SimpleIntegerProperty(0);
		customPieceOpenId = new SimpleStringProperty();
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

	public SimpleIntegerProperty getSelectedTeam() {
		return alternatePiece;
	}

	public void setAlternatePiece(int i) {
		alternatePiece.setValue(i);
	}

	public void setOpenCustomPieceProperty(String id) {
		customPieceOpenId.setValue(id);
	}

	public SimpleStringProperty getOpenCustomPieceProperty() {
		return customPieceOpenId;
	}

	public void exportState() {
		ExportJSON exporter = new ExportJSON(piecesState, gameRulesState, boardState);
		File parentDir = new File("doc/testing_directory/json_export_test");
		exporter.writeToJSON(parentDir);
	}

	private void createDefaultPieces() {
		MovementGrid moves = new MovementGrid();

		createDefaultPiece("pawn", 1, moves);
		createDefaultPiece("knight", 3, moves);
		createDefaultPiece("bishop", 3, moves);
		createDefaultPiece("rook", 5, moves);
		createDefaultPiece("queen", 9, moves);
		createDefaultPiece("king", 99, moves);
	}

	private void createDefaultPiece(String name, int val, MovementGrid moves) {
		getPiecesState().createDefaultPiece(name);
		EditorPiece piece = getPiecesState().getPiece(name);
		piece.setImage(0, new Image("images/pieces/white/" + name + ".png"));
		piece.setImage(1, new Image("images/pieces/black/" + name + ".png"));
		piece.setPieceName(name);
		piece.setPointValue(val);
		piece.setMovementGrid(moves, 0);
	}

	private void setDefaultGameRules() {
		gameRulesState.setTurnCriteria("Linear");

		ArrayList<ArrayList<String>> winConditions = new ArrayList<>();
		ArrayList<String> winConditionsDefaultStalemate = new ArrayList<>();
		ArrayList<String> winConditionsDefaultCheckmate = new ArrayList<>();
		winConditionsDefaultStalemate.add("Stalemate");
		winConditionsDefaultCheckmate.add("Checkmate");
		winConditions.add(winConditionsDefaultCheckmate);
		winConditions.add(winConditionsDefaultStalemate);
		gameRulesState.setWinConditions(winConditions);

		ArrayList<String> colors = new ArrayList<>();
		colors.add("black");
		colors.add("white");
		gameRulesState.setColors(colors);

		HashMap<Integer, ArrayList<Integer>> teamOpponents = new HashMap<>();
		int team0 = 0;
		int team1 = 1;
		teamOpponents.put(team0, new ArrayList<>());
		teamOpponents.put(team1, new ArrayList<>());
		teamOpponents.get(team0).add(team1);
		teamOpponents.get(team1).add(team0);
		gameRulesState.setTeamOpponents(teamOpponents);

		ArrayList<ArrayList<String>> validStateCheckers = new ArrayList<>();
		ArrayList<String> defaultValidStateCheckers = new ArrayList<>();
		defaultValidStateCheckers.add("Check");
		validStateCheckers.add(defaultValidStateCheckers);
		gameRulesState.setValidStateCheckers(validStateCheckers);
	}
}
