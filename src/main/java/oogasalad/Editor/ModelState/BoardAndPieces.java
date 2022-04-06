package oogasalad.Editor.ModelState;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class BoardAndPieces {
  private PiecesState myPiecesState;
  private BoardState myBoardState;

  public BoardAndPieces(){
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
