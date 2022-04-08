package oogasalad.Frontend.Editor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Editor.ModelState.PiecesState.MovementRules;
import oogasalad.Frontend.Editor.Board.BoardEditor;
import oogasalad.Frontend.Editor.Piece.PieceEditor;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.util.View;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import java.util.*;


public class GameEditorView extends View {
	private static String selectedPieceId;
	private final BoardEditor myBoardEditor;
	private final Map<String, PieceEditor> myPieceEditors;
	private final TabPane myTabs;
	private int pieceEditorCount = 0;
	private final EditorController myController;

	public GameEditorView(ViewManager viewManager) {
		super(viewManager);
		myController = new EditorController();
		createDefaultPieces();
		selectedPieceId = "default_pawn";
		myBoardEditor = new BoardEditor(myController);
		myPieceEditors = new HashMap<>();
		Tab boardTab = makeTab(getLanguageResource("Board"), myBoardEditor.getNode());
		boardTab.setClosable(false);
		myTabs = new TabPane(boardTab);
		myTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	public static String getSelectedPieceId() {
		return selectedPieceId;
	}

	public static void setSelectedPieceId(String s) {
		selectedPieceId = s;
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setTop(makeMenu());
		ret.setCenter(myBoardEditor.getNode());
		return ret;
	}

	private void newCustomPiece() {
		pieceEditorCount++;
		PieceEditor newPieceEditor = new PieceEditor(myController, String.valueOf(pieceEditorCount));
		myPieceEditors.put("custom" + pieceEditorCount, newPieceEditor);
		Tab newTab = makeTab(getTabTitle(), newPieceEditor.getNode());
		newTab.setOnClosed((e) -> myPieceEditors.remove(newPieceEditor.getId()));
		myTabs.getTabs().add(newTab);
		myTabs.getSelectionModel().select(newTab);
	}

	private String getTabTitle() {
		return getLanguageResource("CustomPiece") + " " + pieceEditorCount;
	}

	private Tab makeTab(String name, Node content) {
		Tab ret = new Tab(name);
		ret.setContent(content);
		return ret;
	}

	private Node makeMenu() {
		GridPane ret = new GridPane();
		GridPane buttons = new GridPane();
		buttons.add(makeExitButton(), 0, 0);
		Button addCustomPieceButton = ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource("NewCustomPiece"), "newCustomPiece",
				(e) -> newCustomPiece());
		buttons.add(addCustomPieceButton, 1, 0);
		GridPane.setFillHeight(addCustomPieceButton, true);
		ret.add(buttons, 0, 0);
		ret.add(myTabs, 0, 1);
		return ret;
	}

	private void createDefaultPieces() {
		// Rook
		MovementRules rookMovement = new MovementRules();
		myController.getPiecesState().createCustomPiece(5, 0, new Image("images/pieces/black/rook.png"), rookMovement, "default_rook", "Rook");
		myController.getPiecesState().createCustomPiece(5, 1, new Image("images/pieces/black/rook.png"), rookMovement, "default_rook", "Rook");
	}
}