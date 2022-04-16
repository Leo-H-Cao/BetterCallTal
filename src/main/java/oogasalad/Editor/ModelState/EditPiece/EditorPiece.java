package oogasalad.Editor.ModelState.EditPiece;

import java.util.ArrayList;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class EditorPiece {
  private MovementGrid movementGrid;
  private Property<Image> image0;
  private Property<Image> image1;
  private String pieceID;
  private boolean mainPiece;
  private ArrayList<String> customMoves;

  public EditorPiece(String pieceID){
    this.pieceID = pieceID;
    movementGrid = new MovementGrid();
    mainPiece = false;
    image0 = new SimpleObjectProperty<>();
    image1 = new SimpleObjectProperty<>();
  }

  public MovementGrid getMovementGrid() {
    return movementGrid;
  }

  public void setMainPiece(boolean main){
    mainPiece = main;
  }

  public boolean getMainPiece(){
    return mainPiece;
  }

  public String getPieceID() {
    return pieceID;
  }

  public void setTile(int x, int y, PieceGridTile tileStatus){
    movementGrid.setTile(x, y, tileStatus);
  }

  public void setInfiniteTiles(int dirX, int dirY){
    movementGrid.setTileInfinite(dirX, dirY);
  }

  public void removeInfiniteTiles(int dirX, int dirY) {
    movementGrid.removeTileInfinite(dirX, dirY);
  }

  public PieceGridTile getTileStatus(int x, int y){
    return movementGrid.getTileStatus(x, y);
  }

  public void setImage(int team, Image image) {
    if(team == 0){image0.setValue(image);}
    else{image1.setValue(image);}
  }

  public Property<Image> getImage(int team){
    if(team == 0){return image0;}
    else{return image1;}
  }

  public void setCustomMoves(ArrayList<String> customMoves){
    this.customMoves = customMoves;
  }

  public ArrayList<String> getCustomMoves(){
    return customMoves;
  }
}
