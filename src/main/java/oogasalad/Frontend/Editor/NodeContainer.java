package oogasalad.Frontend.Editor;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Screen;
import oogasalad.Frontend.Editor.Board.PieceLibrary;
import oogasalad.Frontend.util.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class NodeContainer {
	protected static final Logger LOG = LogManager.getLogger(PieceLibrary.class);
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	private Node myNode;
	private final Controller myBackend;

	public NodeContainer(Controller controller) {
		myBackend = controller;
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

	protected <T> Optional<T> getBackend(Class<T> type) {
		if(myBackend.getClass() == type) {
			return (Optional<T>)Optional.of(myBackend);
		} else {
			return Optional.empty();
		}
	}

	protected abstract Node makeNode();
}
