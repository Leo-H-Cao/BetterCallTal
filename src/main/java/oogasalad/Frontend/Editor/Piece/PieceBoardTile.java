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
	public int SIZE;
	private final Property<PieceGridTile> status;
	private final Property<Image> myImage;
	private final int myX, myY, myTeam;
	private final String myId;

	public PieceBoardTile(int x, int y, PieceGridTile type, String id, int team) {
		myX = x;
		myY = y;
		myId = id;
		myTeam = team;
		status = new SimpleObjectProperty(type);
		myImage = getEditorBackend().getPiecesState().getPiece(myId).getImage(myTeam);
		myResources.ifPresent(e -> SIZE = Integer.parseInt(e.getString("Size")));
	}

	@Override
	protected Node makeNode() {
		return makeTile();
	}

	private Node makeTile() {
		Rectangle rect = new Rectangle(SIZE, SIZE, status.getValue().getColor());
		StackPane ret = new StackPane(rect);
		Coordinate pieceLocation = getEditorBackend().getPiecesState().getPiece(myId).getMovementGrid(myTeam).getPieceLocation();
		ImageView pieceImage = new ImageView();
		if(pieceLocation.getRow() == myX && pieceLocation.getCol() == myY) {
			pieceImage.setImage(myImage.getValue());
			pieceImage.setFitHeight(SIZE - PADDING);
			pieceImage.setFitWidth(SIZE - PADDING);
			pieceImage.setCache(true);
			pieceImage.setPreserveRatio(true);
			ret.getChildren().add(pieceImage);
		}

		status.addListener((ob, ov, nv) -> rect.setFill(nv.getColor()));
		myImage.addListener((ob, ov, nv) -> pieceImage.setImage(nv));

		// Update tile color when clicked
		if(status.getValue() != PieceGridTile.PIECE) {
			ButtonFactory.addAction(ret, (e) -> {
				PieceGridTile type = getEditorBackend().getSelectedPieceEditorType().getValue();
				getEditorBackend().getPiecesState().getPiece(myId).setTile(myX, myY, type, myTeam);
				status.setValue(type);
			});
		}


		ret.hoverProperty().addListener((ob, ov, nv) -> {
			if(nv && status.getValue() == PieceGridTile.CLOSED) {
				myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("HoverColor"))));
			} else {
				rect.setFill(status.getValue().getColor());
			}
		});

		return ret;
	}
}
