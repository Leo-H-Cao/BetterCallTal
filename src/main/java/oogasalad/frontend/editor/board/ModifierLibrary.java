package oogasalad.frontend.editor.board;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import oogasalad.frontend.editor.EditorBackend;
import oogasalad.frontend.util.LabelledContainer;
import java.util.ArrayList;
import java.util.Collection;

public class ModifierLibrary extends LabelledContainer {
	public ModifierLibrary(EditorBackend controller) {
		super("Modifier Library", controller);
	}

	@Override
	protected Collection<Node> fillContent() {
		ArrayList<Node> ret = new ArrayList<>();
		ret.add(new Circle(50, Paint.valueOf("blue")));
		return ret;
	}
}
