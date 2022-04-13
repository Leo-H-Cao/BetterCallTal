package oogasalad.Frontend.Editor.Piece;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.Editor.ModelState.EditPiece.PieceGridTile;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.NodeContainer;
import oogasalad.GamePlayer.Movement.Coordinate;

public class PieceBoardTile extends NodeContainer {
	public static int SIZE = 80;
	private Property<PieceGridTile> status;
	private int myX, myY;
	private String myId;

	public PieceBoardTile(int x, int y, PieceGridTile type, String id) {
		myX = x;
		myY = y;
		myId = id;
		status = new SimpleObjectProperty(type);
		myResources.ifPresent((e) -> {
			getEditorBackend().getEditorPiece(myId).setImage(0, new Image(e.getString("DefaultImage")));
		});
	}

	@Override
	protected Node makeNode() {
		return makeTile();
	}

	private Node makeTile() {
		Rectangle rect = new Rectangle(SIZE, SIZE, getTileColor(status.getValue()));
		StackPane ret = new StackPane(rect);
		Coordinate pieceLocation = getEditorBackend().getEditorPiece(myId).getMovementGrid().getPieceLocation();
		if(pieceLocation.getRow() == myX && pieceLocation.getCol() == myY) {
			ImageView pieceImage = new ImageView(getEditorBackend().getEditorPiece(myId).getImage(0));
			pieceImage.setFitHeight(SIZE - 5);
			pieceImage.setCache(true);
			pieceImage.setPreserveRatio(true);
			ret.getChildren().add(pieceImage);
		}
		status.addListener((ob, ov, nv) -> rect.setFill(getTileColor(nv)));

		// Update tile color when clicked
		ButtonFactory.addAction(ret, (e) -> {
			PieceGridTile type = getEditorBackend().getSelectedPieceEditorType();
			getEditorBackend().getEditorPiece(myId).setTileOpen(myX, myY);
			status.setValue(type);
		});


		ret.hoverProperty().addListener((ob, ov, nv) -> {
			if(nv && status.getValue() == PieceGridTile.CLOSED) {
				myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("HoverColor"))));
			} else {
				rect.setFill(getTileColor(status.getValue()));
			}
		});

		return ret;
	}

	private Paint getTileColor(PieceGridTile type) {
		if(myResources.isPresent()) {
			switch (type) {
				case CLOSED, PIECE -> {
					return Paint.valueOf(myResources.get().getString("DefaultColor"));
				}
				case OPEN -> {
					return Paint.valueOf(myResources.get().getString("SelectedColor"));
				}
				case INFINITY -> {
					return Paint.valueOf(myResources.get().getString("InfinityColor"));
				}
				default -> {
					return Paint.valueOf(myResources.get().getString("ErrorColor"));
				}
			}
		}
		else {
			return Paint.valueOf("#000");
		}
	}
}
