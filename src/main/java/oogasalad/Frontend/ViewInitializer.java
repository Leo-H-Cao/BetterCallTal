package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.editor.GameEditorView;
import oogasalad.Frontend.game.GameView;
import oogasalad.Frontend.menu.HomeView;
import oogasalad.Frontend.menu.HostGame;
import oogasalad.Frontend.util.View;
import java.util.ResourceBundle;

public class ViewInitializer {
	// Bundle made static so all classes can easily access the language

	private final Stage myStage;

	public ViewInitializer(Stage stage, ResourceBundle rb) {
		myStage = stage;
		View.setLanguage(rb);
		View homeView = new HomeView(myStage);
		View.addView(homeView);
		View.addView(new GameView(myStage));
		View.addView(new GameEditorView(myStage));
		View.addView(new HostGame(myStage));
		myStage.setScene(homeView.getScene());
	}
}
