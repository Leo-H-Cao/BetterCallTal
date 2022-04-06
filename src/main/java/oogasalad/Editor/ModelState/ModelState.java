package oogasalad.Editor.ModelState;

public abstract class ModelState{

  protected EditorBoard myEditorBoard;
  protected PiecesManager piecesManager;
  protected GameRules myGameRules;

  public ModelState(){
    myEditorBoard = new EditorBoard();
    piecesManager = new PiecesManager();
    myGameRules = new GameRules();
  }

  public abstract EditorPiece createCustomPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName, int startX, int startY);

  public abstract void changePieceMovement(String pieceID, MovementRules movementRules);

  public abstract void setPiecePointValue(String pieceID, int points);

  public abstract void setPieceName(String pieceID, String name);

  public abstract void changeBoardSize(int width, int height);

  public abstract void addTileEffect(int x, int y, String effect);

  public abstract void deleteTileEffect(int x, int y);

  public abstract int getBoardWidth();

  public abstract int getBoardHeight();
}
