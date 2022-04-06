package oogasalad.Frontend;

import javafx.stage.Stage;
import oogasalad.Frontend.Editor.GameEditorView;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.Menu.HostGame;
import oogasalad.GamePlayer.Board.ChessBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class MainView {
	// Bundle made static so all classes can easily access the language
	private static ResourceBundle langBundle;

	private final Collection<View> myViews;
	private final Stage stage;


	public MainView(Stage stage, ResourceBundle rb) {
		myViews = new ArrayList<>();
		this.stage = stage;
		langBundle = rb;
		addView(new HomeView(this));
		addView(new GameView(this));
		addView(new GameEditorView(this));
		addView(new HostGame(this));

		// Changes the current scene to the home page on initialization
		myViews.stream().filter((e) -> e.getClass() == HomeView.class).forEach(this::changeScene);
	}

	private void addView(View view) {
		myViews.add(view);
	}

	public Collection<View> getViews() {
		return myViews;
	}

	/**
	 * The Language Resource Bundle is public so that if the user changes languages, only need to change it in this class.
	 * @return Resource Bundle of selected language
	 */
	public static ResourceBundle getLanguage() {
		return langBundle;
	}

	public void changeScene(View viewClass) {
		stage.setScene(viewClass.getScene());
		stage.setTitle(viewClass.getTitle());
	}

	public void setUpGameView(ChessBoard board, int player) {
		//TODO: call GameView SetUpBoard() w this method
		}
}
