package oogasalad.Editor.ModelState.EditPiece;

import java.util.ArrayList;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class EditorPiece {
  private MovementGrid movementGrid;
  private Property<Image> image0;
  private Property<Image> image1;
  private final String pieceID;
  private boolean mainPiece;
  private ArrayList<String> customMoves;
  private int pointValue;
  private Property<String> pieceName;

  public EditorPiece(String pieceID){
    Image defaultMainPieceImage = new Image("images/pieces/white/rook.png");
    Image defaultAltPieceImage = new Image("images/pieces/black/rook.png");
    this.pieceID = pieceID;
    movementGrid = new MovementGrid();
    mainPiece = false;
    pieceName = new SimpleStringProperty();
    image0 = new SimpleObjectProperty<>(defaultMainPieceImage);
    image1 = new SimpleObjectProperty<>(defaultAltPieceImage);
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

  public void setMovementGrid(MovementGrid movementGrid) {
    this.movementGrid = movementGrid;
  }

  public boolean isMainPiece() {
    return mainPiece;
  }

  public int getPointValue() {
    return pointValue;
  }

  public void setPointValue(int pointValue) {
    this.pointValue = pointValue;
  }

  public Property<String> getPieceName() {
    return pieceName;
  }

  public void setPieceName(String pieceName) {
    this.pieceName.setValue(pieceName);
  }

  @Override
  public String toString() {
    return pieceID;
  }
}
