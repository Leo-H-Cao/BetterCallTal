package oogasalad.frontend.editor;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.frontend.util.Controller;

public class EditorBackend extends Controller {
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
