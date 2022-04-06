package oogasalad.Editor.ModelState;

import oogasalad.Editor.ModelState.BoardState.EditorBoard;
import oogasalad.Editor.ModelState.PiecesState.EditorPiece;
import oogasalad.Editor.ModelState.PiecesState.MovementRules;
import oogasalad.Editor.ModelState.PiecesState.PiecesManager;

public abstract class ModelState{

  protected EditorBoard myEditorBoard;
  protected PiecesManager piecesManager;
  protected GameRules myGameRules;

  public ModelState(){
    myEditorBoard = new EditorBoard();
    piecesManager = new PiecesManager();
    myGameRules = new GameRules();
  }
}
