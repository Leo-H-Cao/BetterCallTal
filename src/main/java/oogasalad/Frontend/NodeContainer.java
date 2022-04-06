package oogasalad.Frontend;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Screen;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class NodeContainer {
	protected Rectangle2D myScreenSize;
	private Node myNode;
	private ResourceBundle myResources;

	public NodeContainer() {
		myScreenSize = Screen.getPrimary().getVisualBounds();
		try {
			myResources = ResourceBundle.getBundle(getClass().getName());
		} catch (MissingResourceException e) {
			// Do nothing if file isn't found
		}
	}

	public Node getNode() {
		if(myNode == null) {
			myNode = new Group(makeNode());
		}
		return myNode;
	}

	/**
	 * @return Parent node of this class. This function is called the first time getNode is called and the result is saved in myNode
	 */
	protected abstract Node makeNode();
}
