package oogasalad.Editor.ModelState;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class EditorBackend {
  private final PiecesState myPiecesState;
  private final BoardState myBoardState;

  public EditorBackend(){
    myPiecesState = new PiecesState();
    myBoardState = new BoardState(myPiecesState.getPiecesManager());
  }

  public PiecesState getPiecesState(){
    return myPiecesState;
  }

  public BoardState getBoardState(){
    return myBoardState;
  }

}
