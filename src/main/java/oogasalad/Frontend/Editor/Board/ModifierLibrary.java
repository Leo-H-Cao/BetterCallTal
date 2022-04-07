package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import oogasalad.Frontend.Editor.EditorController;
import oogasalad.Frontend.Editor.LabelledContainer;
import java.util.ArrayList;
import java.util.Collection;

public class ModifierLibrary extends LabelledContainer {
	public ModifierLibrary(EditorController controller) {
		super("Modifier Library", controller);
	}

	@Override
	protected Collection<Node> fillContent() {
		ArrayList<Node> ret = new ArrayList<>();
		ret.add(new Circle(50, Paint.valueOf("blue")));
		return ret;
	}
}
