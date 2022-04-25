package oogasalad.Frontend.Editor.Board;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.NodeContainer;

import java.util.concurrent.atomic.AtomicInteger;

public class ChessBoardTile extends NodeContainer {
	private final double myXSizeMultiplier, myYSizeMultiplier;
	private final int myX;
	private final int myY;
	private final boolean alt;
	private boolean filled = false;

	public ChessBoardTile(int x, int y, boolean toggled, double xSize, double ySize) {
		myX = x;
		myY = y;
		alt = toggled;
		myXSizeMultiplier = xSize;
		myYSizeMultiplier = ySize;
	}

	@Override
	protected Node makeNode() {
		return makeGridTile();
	}

	private Node makeGridTile() {



		AtomicInteger size = new AtomicInteger(-1);
		myResources.ifPresentOrElse(e -> size.set(Integer.parseInt(e.getString("Size"))), () -> size.set(50));

		StackPane ret;
		ImageView image = new ImageView();
		image.setFitWidth(size.get() * myXSizeMultiplier - PADDING);
		image.setFitHeight(size.get() * myYSizeMultiplier - PADDING);
		image.setPreserveRatio(true);
		image.setCache(true);

		image.setImage(getEditorBackend().getBoardState().getTile(myX, myY).getImg());

		Rectangle rect = new Rectangle(myXSizeMultiplier*size.get(), myYSizeMultiplier*size.get());
		if (alt) {
			myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("BaseColor"))));
		} else {
			myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("AltColor"))));
		}
		ret = new StackPane();
		ret.getChildren().addAll(rect, image);
		ButtonFactory.addAction(ret, (e) -> {
			if(filled) {
				clearTile(image);
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

		// Update piece image if it gets updated in the editor
		getEditorBackend().getPiecesState().getPiece(selectedId).getImage(selectedTeam).addListener((ob, ov, nv) -> image.setImage(nv));
		filled = true;
	}

	private void setModifierImage() {


		filled = true;
	}

	private void clearTile(ImageView image) {
		filled = false;
		image.setImage(null);
		getEditorBackend().getBoardState().clearTile(myX, myY);
	}
}
