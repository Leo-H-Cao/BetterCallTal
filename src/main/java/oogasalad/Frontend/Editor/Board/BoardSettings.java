package oogasalad.Frontend.Editor.Board;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import oogasalad.Frontend.util.LabelledContainer;

public class BoardSettings extends LabelledContainer {
	public static final int MAX_SIZE = 50;

	public BoardSettings() {
		super("Board Settings");
	}

	@Override
	protected Node fillContent() {
		Node widthSelector = makeSpinner(true);
		Node heightSelector = makeSpinner(false);

		GridPane ret = new GridPane();
		ret.add(widthSelector, 0, 0);
		ret.add(heightSelector, 0, 1);


		return ret;
	}

	private Node makeSpinner(boolean width) {
		int defaultValue = getEditorBackend().getBoardState().getBoardHeight().getValue();
		SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, MAX_SIZE, defaultValue);
		factory.setValue(8);
		Spinner<Integer> spinner = new Spinner<>(factory);

		spinner.valueProperty().addListener((ob, ov, nv) -> System.out.println(nv));

		Label text = new Label();
		if (width) {
			text.setText("board width");
			spinner.valueProperty().addListener((ob, ov, nv) -> getEditorBackend().getBoardState().setBoardWidth(nv));
		} else {
			text.setText("board height");
			spinner.valueProperty().addListener((ob, ov, nv) -> getEditorBackend().getBoardState().setBoardHeight(nv));
		}
		GridPane ret = new GridPane();
		ret.add(text, 0, 0);
		ret.add(spinner, 1, 0);
		ret.setHgap(GAP);

		GridPane.setHalignment(text, HPos.CENTER);
		GridPane.setHalignment(spinner, HPos.CENTER);

		return ret;
	}
}
