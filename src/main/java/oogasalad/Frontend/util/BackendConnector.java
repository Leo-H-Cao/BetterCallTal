package oogasalad.Frontend.util;

import oogasalad.Editor.ModelState.EditorBackend;
import oogasalad.Frontend.Game.GameBackend;

import java.util.ResourceBundle;

public class BackendConnector {
	private static EditorBackend myEditorBackend;
	private static GameBackend myGameBackend;
	private static ResourceBundle myLanguage;

	public static void initBackend(ResourceBundle rb) {
		myLanguage = rb;
		myGameBackend = new GameBackend();
		myEditorBackend = new EditorBackend();
	}

	/**
	 * @param s String to find
	 * @param className If specified, prefixes className to bundle search
	 * @return String corresponding to s from the language bundle
	 */
	public static <T> String getFrontendWord(String s, Class<T> className) {
		return myLanguage.getString(className.getSimpleName() + s);
	}

	/**
	 * @param s String to search for in the language bundle
	 * @return String in correct language
	 */
	public static String getFrontendWord(String s) {
		return myLanguage.getString(s);
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
