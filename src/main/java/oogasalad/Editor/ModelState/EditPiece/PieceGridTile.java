package oogasalad.Editor.ModelState.EditPiece;

import javafx.scene.paint.Paint;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum PieceGridTile {
  CLOSED,
  OPEN,
  INFINITY,
  PIECE,
  CAPTURE,
  OPENANDCAPTURE,
  INFINITECAPTURE;

  public final ResourceBundle myBundle;

  PieceGridTile() {
    myBundle = ResourceBundle.getBundle(getClass().getName());
  }
  public Paint getColor() {
    String ret;
    try {
      ret = myBundle.getString(this.toString());
    } catch(MissingResourceException e) {
      ret = myBundle.getString("COLOR_NOT_FOUND");
    }
    return Paint.valueOf(ret);
  }
}
