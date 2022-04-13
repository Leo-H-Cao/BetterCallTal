package oogasalad.Frontend.util;

import oogasalad.Editor.ModelState.EditorBackend;
import oogasalad.Frontend.Game.GameBackend;

public class BackendConnector {
	private static EditorBackend myEditorBackend;
	private static GameBackend myGameBackend;

	public static void initBackend() {
		myGameBackend = new GameBackend();
		myEditorBackend = new EditorBackend();
	}

	protected EditorBackend getEditorBackend() {
		if(myEditorBackend != null) {
			return myEditorBackend;
		} else {
			return null;
		}
	}

	protected GameBackend getGameBackend() {
		if(myGameBackend != null) {
			return myGameBackend;
		} else {
			return null;
		}
	}
}
