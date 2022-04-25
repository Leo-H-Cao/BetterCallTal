package oogasalad.Frontend.util;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public abstract class LabelledContainer extends NodeContainer {
	private String myTitle;

	public LabelledContainer(String s) {
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
		Group g = new Group(fillContent());
		ret.add(g, 0, 1);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(ret);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setMinViewportWidth(300);
		scrollPane.setPrefViewportWidth(400);
		scrollPane.setMaxHeight(600);
		return scrollPane;
	}

	private Node makeTitle() {
		Label ret = new Label(myTitle);
		ret.setFont(new Font(36));
		ret.setTextAlignment(TextAlignment.CENTER);
		return ret;
	}

	/**
	 * @return Collection of Nodes to be set as children of the flow pane
	 */
	protected abstract Node fillContent();
}
