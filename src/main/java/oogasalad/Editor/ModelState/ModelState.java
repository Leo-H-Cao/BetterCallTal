package oogasalad.Editor.ModelState;

public class ModelState{

  protected EditorBoard myEditorBoard;
  protected PiecesManager piecesManager;
  protected GameRules myGameRules;

  public ModelState(){
    myEditorBoard = new EditorBoard();
    piecesManager = new PiecesManager();
    myGameRules = new GameRules();
  }
}
