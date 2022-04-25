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

  public static int SIZE = 85;
  private Property<PieceGridTile> status;
  private Property<Image> myImage;
  private int myX, myY;
  private String myId;

  public PieceBoardTile(int x, int y, PieceGridTile type, String id) {
    myX = x;
    myY = y;
    myId = id;
    status = new SimpleObjectProperty(type);
    myImage = getEditorBackend().getPiecesState().getPiece(myId).getImage(0);
  }

  @Override
  protected Node makeNode() {
    return makeTile();
  }

  private Node makeTile() {
    Rectangle rect = new Rectangle(SIZE, SIZE, getTileColor(status.getValue()));
    StackPane ret = new StackPane(rect);
    Coordinate pieceLocation = getEditorBackend().getPiecesState().getPiece(myId).getMovementGrid(0)
        .getPieceLocation();
    ImageView pieceImage = new ImageView();
    if (pieceLocation.getRow() == myX && pieceLocation.getCol() == myY) {
      pieceImage.setImage(myImage.getValue());
      pieceImage.setFitHeight(SIZE - 5);
      pieceImage.setFitWidth(SIZE - 5);
      pieceImage.setCache(true);
      pieceImage.setPreserveRatio(true);
      ret.getChildren().add(pieceImage);
    }
    status.addListener((ob, ov, nv) -> rect.setFill(getTileColor(nv)));
    myImage.addListener((ob, ov, nv) -> pieceImage.setImage(nv));

    // Update tile color when clicked
    if (status.getValue() != PieceGridTile.PIECE) {
      ButtonFactory.addAction(ret, (e) -> {
        PieceGridTile type = getEditorBackend().getSelectedPieceEditorType().getValue();
        getEditorBackend().getPiecesState().getPiece(myId).setTile(myX, myY, type, 0);
        status.setValue(type);
      });
    }

    ret.hoverProperty().addListener((ob, ov, nv) -> {
      if (nv && status.getValue() == PieceGridTile.CLOSED) {
        myResources.ifPresent((e) -> rect.setFill(Paint.valueOf(e.getString("HoverColor"))));
      } else {
        rect.setFill(getTileColor(status.getValue()));
      }
    });

    return ret;
  }

  private Paint getTileColor(PieceGridTile type) {
    if (myResources.isPresent()) {
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
    } else {
      return Paint.valueOf("#000");
    }
  }
}
