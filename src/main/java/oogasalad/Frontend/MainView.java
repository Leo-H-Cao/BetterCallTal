package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;

import java.util.ResourceBundle;

public class MainView {

	// Bundle made static so all classes can easily access the language
	private static ResourceBundle langBundle;

	private GameView myGameView;
	private GameEditorView myEditorView;
	private Stage stage;


	public MainView(Stage stage, ResourceBundle rb) {
		this.stage = stage;
		langBundle = rb;
		myGameView = new GameView(stage);
		myEditorView = new GameEditorView(stage);
	}

	public static ResourceBundle getLanguage() {
		return langBundle;
	}
}
