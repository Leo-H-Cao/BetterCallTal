package oogasalad.Frontend.Editor;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.NodeContainer;

import java.util.Collection;

public abstract class LabelledContainer extends NodeContainer {
	private String myTitle;

	public LabelledContainer(String s) {
		super();
		setTitle(s);
	}

	public void setTitle(String s) {
		myTitle = s;
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Node makeLayout() {
		GridPane ret = new GridPane();
		ret.add(makeTitle(), 0, 0);
		FlowPane flowPane = new FlowPane();
		ret.add(flowPane, 0, 1);
		flowPane.setOrientation(Orientation.HORIZONTAL);
		flowPane.getChildren().addAll(fillContent());
		return ret;
	}

	private Node makeTitle() {
		return new Label(myTitle);
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	protected abstract Collection<Node> fillContent();
}
