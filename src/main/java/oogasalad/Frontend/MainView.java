package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.MenuView;

public class MainView {

	private GameView myGameView;
	private GameEditorView myEditorView;
	private MenuView myMenuView;

	public MainView(Stage stage) {

		myGameView = new GameView(stage);
		myEditorView = new GameEditorView(stage);
		myMenuView = new MenuView(stage);


	}
}
