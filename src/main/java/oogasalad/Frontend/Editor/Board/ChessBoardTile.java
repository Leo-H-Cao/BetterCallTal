package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.NodeContainer;

public class ChessBoardTile extends NodeContainer {
	private final int myXSize, myYSize;
	private final int myX;
	private final int myY;
	private final boolean alt;
	private boolean filled = false;

	public ChessBoardTile(int x, int y, boolean toggled, int xSize, int ySize) {
		myX = x;
		myY = y;
		alt = toggled;
		myXSize = xSize;
		myYSize = ySize;
	}

	@Override
	protected Node makeNode() {
		return makeGridTile();
	}

	private Node makeGridTile() {
		StackPane ret;
		ImageView image = new ImageView();
		image.setFitWidth(myXSize - PADDING);
		image.setFitHeight(myYSize - PADDING);
		image.setPreserveRatio(true);
		image.setCache(true);
		Rectangle rect = new Rectangle(myXSize, myYSize);
		if (alt) {
			myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("BaseColor"))));
		} else {
			myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("AltColor"))));
		}
		ret = new StackPane();
		ret.getChildren().addAll(rect, image);
		ButtonFactory.addAction(ret, (e) -> {
			if(filled) {
				removePieceImage(image);
			} else {
				setPieceImage(image);
			}
		});
		return ret;
	}

	private void setPieceImage(ImageView image) {
		String selectedId = String.valueOf(getEditorBackend().getSelectedPieceId().getValue());
		int selectedTeam = getEditorBackend().getSelectedTeam().getValue();

		getEditorBackend().getBoardState().setPieceStartingLocation(selectedId, myX, myY, selectedTeam);

		image.setImage(getEditorBackend().getPiecesState().getPiece(selectedId).getImage(selectedTeam).getValue());

		getEditorBackend().getPiecesState().getPiece(selectedId).getImage(selectedTeam).addListener((ob, ov, nv) -> image.setImage(nv));
		filled = true;
	}

	private void removePieceImage(ImageView image) {
		filled = false;
		image.setImage(null);
		getEditorBackend().getBoardState().clearTile(myX, myY);
	}
}
