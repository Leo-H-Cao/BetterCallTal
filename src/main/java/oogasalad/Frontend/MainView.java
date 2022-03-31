package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.MenuView;

import java.util.ResourceBundle;

public class MainView {

	// Bundle made static so all classes can easily access the language
	private static ResourceBundle language;

	private GameView myGameView;
	private GameEditorView myEditorView;
	private MenuView myMenuView;
	private Stage stage;


	public MainView(Stage stage) {
		this.stage = stage;
		myGameView = new GameView(stage);
		myEditorView = new GameEditorView(stage);
		myMenuView = new MenuView(stage);
	}
}
