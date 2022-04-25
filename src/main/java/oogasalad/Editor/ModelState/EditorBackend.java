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

import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.CAPTURE;
import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.INFINITECAPTURE;
import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.OPEN;
import static oogasalad.Editor.ModelState.EditPiece.PieceGridTile.OPENANDCAPTURE;

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
		createDefaultPiece("pawn", 1, createWhitePawnDefaultMoves());
		createDefaultPiece("knight", 3, createKnightDefaultMoves());
		createDefaultPiece("bishop", 3, createBishopDefaultMoves());
		createDefaultPiece("rook", 5, createRookDefaultMoves());
		createDefaultPiece("queen", 9, createQueenDefaultMoves());
		createDefaultPiece("king", 99, createKingDefaultMoves());
	}

	private void createDefaultPiece(String name, int val, MovementGrid moves) {
		getPiecesState().createDefaultPiece(name);
		EditorPiece piece = getPiecesState().getPiece(name);
		piece.setImage(0, new Image("images/pieces/Default/white/" + name + ".png"));
		piece.setImage(1, new Image("images/pieces/Default/black/" + name + ".png"));
		name = name + "Test";
		piece.setPieceName(name);
		piece.setPointValue(val);
		piece.setMovementGrid(moves, 0);
		if(name.equals("pawnTest")){
			piece.setMovementGrid(createBlackPawnDefaultMoves(), 1);
		}
		else{
			piece.setMovementGrid(moves, 1);
		}
	}

	private MovementGrid createBlackPawnDefaultMoves(){
		MovementGrid moves = new MovementGrid();
		moves.setTile(3,2, OPEN);
		moves.setTile(2,2, CAPTURE);
		moves.setTile(4,2, CAPTURE);
		return moves;
	}

	private MovementGrid createWhitePawnDefaultMoves(){
		MovementGrid moves = new MovementGrid();
		moves.setTile(3,4, OPEN);
		moves.setTile(2,4, CAPTURE);
		moves.setTile(4,4, CAPTURE);
		return moves;
	}

	private MovementGrid createKnightDefaultMoves(){
		MovementGrid moves = new MovementGrid();
		moves.setTile(2,1, OPENANDCAPTURE);
		moves.setTile(4,1, OPENANDCAPTURE);
		moves.setTile(1,2, OPENANDCAPTURE);
		moves.setTile(1,4, OPENANDCAPTURE);
		moves.setTile(6,2, OPENANDCAPTURE);
		moves.setTile(6,4, OPENANDCAPTURE);
		moves.setTile(2,6, OPENANDCAPTURE);
		moves.setTile(4,6, OPENANDCAPTURE);
		return moves;
	}

	private MovementGrid createBishopDefaultMoves(){
		MovementGrid moves = new MovementGrid();
		moves.setTile(2,2, INFINITECAPTURE);
		moves.setTile(4,2, INFINITECAPTURE);
		moves.setTile(2, 4, INFINITECAPTURE);
		moves.setTile(4,4, INFINITECAPTURE);
		return moves;
	}

	private MovementGrid createRookDefaultMoves(){
		MovementGrid moves = new MovementGrid();
		moves.setTile(3,2, INFINITECAPTURE);
		moves.setTile(2,3, INFINITECAPTURE);
		moves.setTile(4, 3, INFINITECAPTURE);
		moves.setTile(3,4, INFINITECAPTURE);
		return moves;
	}

	private MovementGrid createQueenDefaultMoves(){
		MovementGrid moves = createBishopDefaultMoves();
		moves.setTile(3,2, INFINITECAPTURE);
		moves.setTile(2,3, INFINITECAPTURE);
		moves.setTile(4, 3, INFINITECAPTURE);
		moves.setTile(3,4, INFINITECAPTURE);
		return moves;
	}

	private MovementGrid createKingDefaultMoves(){
		MovementGrid moves = new MovementGrid();
		moves.setTile(3,2, OPENANDCAPTURE);
		moves.setTile(2,3, OPENANDCAPTURE);
		moves.setTile(4, 3, OPENANDCAPTURE);
		moves.setTile(3,4, OPENANDCAPTURE);
		moves.setTile(2,2, OPENANDCAPTURE);
		moves.setTile(4,2, OPENANDCAPTURE);
		moves.setTile(2, 4, OPENANDCAPTURE);
		moves.setTile(4,4, OPENANDCAPTURE);
		return moves;
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
