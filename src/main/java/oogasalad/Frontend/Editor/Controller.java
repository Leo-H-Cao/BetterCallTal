package oogasalad.Frontend.Editor;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class Controller {
	private PiecesState myPiecesState;
	private BoardState myBoardState;

	public Controller(){
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
