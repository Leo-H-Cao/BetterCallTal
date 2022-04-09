package oogasalad.frontend;

import javafx.stage.Stage;
import oogasalad.frontend.editor.GameEditorView;
import oogasalad.frontend.game.GameView;
import oogasalad.frontend.menu.HomeView;
import oogasalad.frontend.menu.HostGame;
import oogasalad.frontend.util.View;
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
