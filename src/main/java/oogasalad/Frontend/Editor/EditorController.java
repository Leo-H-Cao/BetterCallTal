package oogasalad.Frontend.Editor;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;

public class EditorController {
	private final PiecesState myPiecesState;
	private final BoardState myBoardState;

	public EditorController(){
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
