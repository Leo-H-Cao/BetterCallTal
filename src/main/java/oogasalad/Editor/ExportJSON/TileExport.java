package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

public class TileExport {
  private int row;
  private int col;
  private ArrayList<String> tileActions;
  private String img;

  public TileExport(int row, int col, String img){
    this.row = row;
    this.col = col;
    tileActions = new ArrayList<>();
    this.img = img;
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
