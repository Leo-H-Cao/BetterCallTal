package oogasalad.Frontend.Editor;

import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Collection;

public abstract class LabelledContainer extends NodeContainer {
	private String myTitle;

	public LabelledContainer(String s, EditorController controller) {
		super(controller);
		setTitle(s);
	}

	public void setTitle(String s) {
		myTitle = s;
	}

	@Override
	protected Node makeNode() {
		return new Group(makeLayout());
	}

	private Node makeLayout() {
		GridPane ret = new GridPane();
		ret.add(makeTitle(), 0, 0);
		FlowPane flowPane = new FlowPane();
		ret.add(flowPane, 0, 1);
		flowPane.setOrientation(Orientation.HORIZONTAL);
		flowPane.getChildren().addAll(fillContent());
		flowPane.setHgap(5);
		flowPane.setVgap(5);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(ret);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
	protected abstract Collection<Node> fillContent();
}
