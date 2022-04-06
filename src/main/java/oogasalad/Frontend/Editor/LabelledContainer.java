package oogasalad.Frontend.Editor;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.NodeContainer;

public abstract class LabelledContainer extends NodeContainer {
	private String myTitle;



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


		return ret;
	}

	private Node makeTitle() {
		return new Label(myTitle);
	}
}
