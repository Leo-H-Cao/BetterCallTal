package oogasalad.Frontend.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Screen;
import oogasalad.Frontend.Editor.EditorController;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class NodeContainer {
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	private Node myNode;
	protected EditorController myController;

	public NodeContainer(EditorController controller) {
		myController = controller;
		myScreenSize = Screen.getPrimary().getVisualBounds();
		try {
			myResources = Optional.of(ResourceBundle.getBundle(getClass().getName()));
		} catch (MissingResourceException e) {
			myResources = Optional.empty();
		}
	}

	public Node getNode() {
		if(myNode == null) {
			myNode = new Group(makeNode());
		}
		return myNode;
	}

	protected abstract Node makeNode();
}
