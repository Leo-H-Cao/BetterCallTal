package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * Class meant to be serialazed into JSON, single tile and the special modifiers of that tile
 * @author Leo Cao
 */
public class TileExport {
  private int row;
  private int col;
  private ArrayList<String> tileActions;
  private String img;

  public TileExport(int row, int col, Image img){
    this.row = row;
    this.col = col;
    tileActions = new ArrayList<>();
    this.img = img != null ? img.getUrl().split("/classes/")[1] : null;
  }

  public void addTileAction(String tileAction){
    tileActions.add(tileAction);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public ArrayList<String> getTileActions() {
    return tileActions;
  }

  public String getImg() {
    return img;
  }
}
