package oogasalad.Editor.ModelState.BoardState;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class BoardState {
  private EditorBoard myEditorBoard;
  private PiecesState piecesState;

  public BoardState(PiecesState piecesState){
    myEditorBoard = new EditorBoard();
    this.piecesState = piecesState;
  }

  public void setWidth(int newValue) {
    myEditorBoard.changeWidth(newValue);
  }

  public void setHeight(int newValue) {
    myEditorBoard.changeHeight(newValue);
  }

  public void setTileEffect(int x, int y, TileEffect effect) {
    myEditorBoard.setTileEffect(x, y, effect);
  }

  public void setTileImage(int x, int y, Image img){
    myEditorBoard.setTileImage(x,y,img);
  }

  public SimpleIntegerProperty getWidth(){
    return myEditorBoard.getWidth();
  }

  public SimpleIntegerProperty getHeight(){
    return myEditorBoard.getHeight();
  }

  public void setPieceStartingLocation(String pieceID, int x, int y, int team) {
    Image pieceImg = piecesState.getPiece(pieceID).getImage(team).getValue();
    myEditorBoard.addPieceStartingLocation(pieceID, x, y, team, pieceImg);
  }

  public EditorCoordinate getPieceLocation(String pieceID){
    return myEditorBoard.getPieceLocation(pieceID);
  }

  public EditorTile getTile(int x, int y){
    return myEditorBoard.getTile(x, y);
  }

  public void clearTile(int x, int y){
    myEditorBoard.clearTile(x, y);
  }
}
