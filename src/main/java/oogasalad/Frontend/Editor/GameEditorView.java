package oogasalad.Frontend.Editor;
/**
 * This class will handle the view for the Game Editor.
 */

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.Editor.Board.BoardEditor;
import oogasalad.Frontend.Editor.Piece.PieceEditor;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;
import java.util.Collection;


public class GameEditorView extends View {
	private BoardEditor myBoardEditor;
	private Collection<PieceEditor> myPieceEditors;
	private TabPane myTabs;

	public GameEditorView(MainView mainView) {
		super(mainView);
		myBoardEditor = new BoardEditor();
		myTabs = new TabPane();
		Tab boardTab = makeTab("Board", myBoardEditor.getNode());
		boardTab.setClosable(false);
		myTabs.getTabs().add(boardTab);
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Tab makeTab(String name, Node content) {
		Tab ret = new Tab();
		ret.setText(name);
		ret.setContent(content);
		return ret;
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setTop(makeMenu());
		ret.setCenter(myBoardEditor.getNode());
		return ret;
	}

	private Node makeMenu() {
		GridPane ret = new GridPane();
		ret.setPrefHeight(5000);
		ret.add(makeExitButton(), 0, 0);
		ret.add(myTabs, 0, 1);
		return ret;
	}
}