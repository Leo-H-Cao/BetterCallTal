package oogasalad.Frontend.Editor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.Editor.Board.BoardEditor;
import oogasalad.Frontend.Editor.Piece.PieceEditor;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.View;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import java.util.ArrayList;
import java.util.Collection;


public class GameEditorView extends View {
	private static String selectedPieceId;
	private final BoardEditor myBoardEditor;
	private final Collection<PieceEditor> myPieceEditors;
	private final TabPane myTabs;

	public GameEditorView(ViewManager viewManager) {
		super(viewManager);
		selectedPieceId = "default_pawn";
		myBoardEditor = new BoardEditor();
		myPieceEditors = new ArrayList<>();
		Tab boardTab = makeTab(getLanguageResource("Board"), myBoardEditor.getNode());
		boardTab.setClosable(false);
		myTabs = new TabPane(boardTab);
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
		PieceEditor newPieceEditor = new PieceEditor();
		myPieceEditors.add(newPieceEditor);
		Tab newTab = makeTab(getLanguageResource("CustomPiece") + " " + myPieceEditors.size(), newPieceEditor.getNode());
		myTabs.getTabs().add(newTab);
		myTabs.getSelectionModel().select(newTab);
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
}