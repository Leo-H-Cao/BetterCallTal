package oogasalad.Frontend.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Screen;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Frontend.Editor.Board.PieceLibrary;
import oogasalad.Frontend.ViewManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class NodeContainer extends BackendConnector {
	protected int PADDING;
	protected static final Logger LOG = LogManager.getLogger(PieceLibrary.class);
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	private Node myNode;

	public NodeContainer() {
		myScreenSize = Screen.getPrimary().getVisualBounds();
		try {
			myResources = Optional.of(ResourceBundle.getBundle(getClass().getName()));
		} catch (MissingResourceException e) {
			myResources = Optional.empty();
		}
		PADDING = Integer.parseInt(ResourceBundle.getBundle("oogasalad.Frontend.util.NodeContainer").getString("Padding"));
	}

	public Node getNode() {
		if(myNode == null) {
			myNode = new Group(makeNode());
		}
		return myNode;
	}

	protected abstract Node makeNode();
}
