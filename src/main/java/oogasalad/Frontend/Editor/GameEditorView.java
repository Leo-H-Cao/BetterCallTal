package oogasalad.Frontend.Editor;
/**
 * This class will handle the view for the Game Editor.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.Editor.Board.BoardEditor;
import oogasalad.Frontend.Editor.Piece.PieceEditor;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;

import java.util.ArrayList;
import java.util.Collection;


public class GameEditorView extends View {
	private BoardEditor myBoardEditor;
	private Collection<PieceEditor> myPieceEditors;
	private TabPane myTabs;

	public GameEditorView(MainView mainView) {
		super(mainView);
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

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setPrefWidth(myScreenSize.getWidth());
		ret.setTop(makeMenu());
		ret.setCenter(myBoardEditor.getNode());
		return ret;
	}

	private void newCustomPiece() {
		PieceEditor newPieceEditor = new PieceEditor();
		myPieceEditors.add(newPieceEditor);
		myTabs.getTabs().add(makeTab(getLanguageResource("CustomPiece") + " " + myPieceEditors.size(), newPieceEditor.getNode()));
	}

	private Tab makeTab(String name, Node content) {
		Tab ret = new Tab(name);
		ret.setContent(content);
		return ret;
	}

	private Node makeMenu() {
		GridPane ret = new GridPane();
		ret.setPrefHeight(5000);
		ret.add(makeExitButton(), 0, 0);
		ret.add(myTabs, 0, 1);
		ret.add(ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource("NewCustomPiece"), "newCustomPiece",
				(e) -> newCustomPiece()), 1, 0);
		return ret;
	}
}