package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.LabelledContainer;

public class BoardSettings extends LabelledContainer {

	public BoardSettings() {
		super("Board Settings");
	}

	@Override
	protected Node fillContent() {
		FlowPane ret;
		CheckBox checkBox = new CheckBox("Place alternate color");
		Button b = ButtonFactory.makeButton(ButtonType.TEXT, "", "", (e) -> {

		});

		ret = new FlowPane(checkBox);
		return ret;
	}
}
