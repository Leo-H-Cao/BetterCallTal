package oogasalad.Frontend.Editor;

import oogasalad.Editor.ModelState.BoardState.BoardState;
import oogasalad.Editor.ModelState.PiecesState.PiecesState;
import oogasalad.Frontend.util.Controller;

public class EditorController extends Controller {
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
