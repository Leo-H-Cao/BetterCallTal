package oogasalad.Editor.ModelState.BoardState;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import oogasalad.Editor.ModelState.PiecesState.EditorCoordinate;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class BoardState {
  private EditorBoard myEditorBoard;

  public BoardState(){
    myEditorBoard = new EditorBoard();
  }

  public void changeBoardSize(int width, int height) {
    myEditorBoard.changeBoardSize(width, height);
  }

  public void setTileEffect(int x, int y, TileEffect effect) {
    myEditorBoard.setTileEffect(x, y, effect);
  }

  public void setTileImage(int x, int y, Image img){
    myEditorBoard.setTileImage(x,y,img);
  }

  public SimpleIntegerProperty getBoardWidth(){
    return myEditorBoard.getBoardWidth();
  }

  public SimpleIntegerProperty getBoardHeight(){
    return myEditorBoard.getBoardHeight();
  }

  public void setPieceStartingLocation(String pieceID, int x, int y, int team) {
    myEditorBoard.addPieceStartingLocation(pieceID, x, y, team);
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
