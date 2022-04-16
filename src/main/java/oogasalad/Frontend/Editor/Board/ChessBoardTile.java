package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.NodeContainer;

public class ChessBoardTile extends NodeContainer {
	public static final int SIZE = 80;
	private final int myX;
	private final int myY;
	private final boolean alt;

	public ChessBoardTile(int x, int y, boolean toggled) {
		myX = x;
		myY = y;
		alt = toggled;
	}

	@Override
	protected Node makeNode() {
		return makeGridTile();
	}

	private Node makeGridTile() {
		StackPane ret;
		ImageView image = new ImageView();
		Rectangle rect = new Rectangle(SIZE, SIZE);
		if (alt) {
			myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("BaseColor"))));
		} else {
			myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("AltColor"))));
		}
		ButtonFactory.addAction(rect, (e) -> {
			String selectedId = String.valueOf(getEditorBackend().getSelectedPieceId().getValue());
			LOG.debug(selectedId);
			getEditorBackend().getBoardState().setPieceStartingLocation(selectedId, myX, myY);
			image.setImage(getEditorBackend().getEditorPiece(selectedId).getImage(0).getValue());
			getEditorBackend().getEditorPiece(selectedId).getImage(0).addListener((ob, ov, nv) -> image.setImage(nv));
		});
		ret = new StackPane();
		ret.getChildren().addAll(rect, image);
		return ret;
	}
}
