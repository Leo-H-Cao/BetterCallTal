package oogasalad.Frontend.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Screen;
import oogasalad.Frontend.Editor.Board.PieceLibrary;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.Game.GameBackend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class NodeContainer {
	protected static final Logger LOG = LogManager.getLogger(PieceLibrary.class);
	protected Rectangle2D myScreenSize;
	protected Optional<ResourceBundle> myResources;
	private Node myNode;
	private final Callable getBackend;

	public <V> NodeContainer(Callable<V> backendConsumer) {
		getBackend = backendConsumer;
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

	protected Optional<EditorController> getEditorBackend() throws Exception {
		Object backend = getBackend.call();
		if(backend.getClass() == EditorController.class) {
			return Optional.of((EditorController)backend);
		} else {
			return Optional.empty();
		}
	}

	protected Optional<GameBackend> getGameBackend() throws Exception {
		Object backend = getBackend.call();
		if(backend.getClass() == EditorController.class) {
			return Optional.of((GameBackend) backend);
		} else {
			return Optional.empty();
		}
	}

	protected abstract Node makeNode();
}
