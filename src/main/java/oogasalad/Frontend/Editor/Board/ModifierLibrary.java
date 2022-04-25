package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import oogasalad.Frontend.util.LabelledContainer;

public class ModifierLibrary extends LabelledContainer {
	public ModifierLibrary() {
		super("Modifier Library");
	}

	@Override
	protected Node fillContent() {
		return new Circle(50, Paint.valueOf("blue"));
	}
}
