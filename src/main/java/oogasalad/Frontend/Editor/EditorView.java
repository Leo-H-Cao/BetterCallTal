package oogasalad.Frontend.Editor;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;
import oogasalad.Frontend.Editor.Board.BoardEditor;
import oogasalad.Frontend.Editor.Piece.PieceEditor;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.View;
import java.util.HashMap;
import java.util.Map;

public class EditorView extends View {
	private final BoardEditor myBoardEditor;
	private final Map<String, PieceEditor> myPieceEditors;
	private final TabPane myTabs;
	private int pieceEditorCount = 0;

	public EditorView(Stage stage) {
		super(stage);
		myBoardEditor = new BoardEditor();
		myPieceEditors = new HashMap<>();
		Tab boardTab = makeTab(new SimpleStringProperty(BackendConnector.getFrontendWord("Board", getClass())), myBoardEditor.getNode());
		boardTab.setClosable(false);
		myTabs = new TabPane(boardTab);
		myTabs.setId("EditorTabPane");
		myTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
	}

	public TabPane getMyTabs() {
		return myTabs;
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setTop(makeMenu());
		ret.setCenter(myBoardEditor.getNode());
		return ret;
	}

	private void newCustomPiece() {
		pieceEditorCount++;
		String newPieceId = "custom" + pieceEditorCount;
		PieceEditor newPieceEditor = new PieceEditor(newPieceId);
		myPieceEditors.put(newPieceId, newPieceEditor);
		getEditorBackend().getPiecesState().createCustomPiece(newPieceId);
		EditorPiece piece = getEditorBackend().getPiecesState().getPiece(newPieceId);
		piece.setPieceName(getPieceName());
		Tab newTab = makeTab(piece.getPieceName(), newPieceEditor.getNode());
		newTab.setOnClosed((e) -> myPieceEditors.remove(newPieceEditor.getId()));
		myTabs.getTabs().add(newTab);
		myTabs.getSelectionModel().select(newTab);
	}

	private String getPieceName() {
		return BackendConnector.getFrontendWord("CustomPiece", getClass()) + " " + pieceEditorCount;
	}

	private Tab makeTab(Property<String> name, Node content) {
		Tab ret = new Tab(name.getValue());
		name.addListener((ob, ov, nv) -> ret.setText(nv));
		ret.setContent(content);
		return ret;
	}

	private Node makeMenu() {
		GridPane ret = new GridPane();
		GridPane buttons = new GridPane();
		buttons.add(makeExitButton(), 0, 0);
		Button addCustomPieceButton = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("NewCustomPiece", getClass()), "newCustomPiece",
				(e) -> newCustomPiece());
		buttons.add(addCustomPieceButton, 1, 0);
		GridPane.setFillHeight(addCustomPieceButton, true);
		ret.add(buttons, 0, 0);
		ret.add(myTabs, 0, 1);
		return ret;
	}
}