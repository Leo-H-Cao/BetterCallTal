package oogasalad.Editor.ModelState;

import java.util.List;
import oogasalad.Editor.API.ModifiesRulesState;

public class GameRulesState extends ModelState implements ModifiesRulesState {

  public GameRulesState(){
    super();
  }

  @Override
  public EditorPiece createCustomPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName, int startX, int startY) {
    return null;
  }

  @Override
  public void changePieceMovement(String pieceID, MovementRules movementRules) {

  }

  @Override
  public void setPiecePointValue(String pieceID, int points) {

  }

  @Override
  public void setPieceName(String pieceID, String name) {

  }

  @Override
  public void changeBoardSize(int width, int height) {

  }

  @Override
  public void addTileEffect(int x, int y, String effect) {

  }

  @Override
  public void deleteTileEffect(int x, int y) {

  }

  @Override
  public void setPieceStartingLocation(String pieceID, int x, int y) {

  }

  @Override
  public void removePiece(int x, int y) {

  }

  @Override
  public int getBoardWidth() {
    return 0;
  }

  @Override
  public int getBoardHeight() {
    return 0;
  }

  @Override
  public void setWinConditions(List<String> winConditions) {
    myGameRules.setWinConditions(winConditions);
  }

  @Override
  public void setTurnCriteria(String turnCriteria) {
    myGameRules.setTurnCriteria(turnCriteria);
  }

  //For testing
  public GameRules getGameRules(){
    return myGameRules;
  }
}
