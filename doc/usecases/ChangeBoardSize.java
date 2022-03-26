package oogasalad;

public class ModelState {

  private EditorBoard startingLocationsBoard;

  public ModelState(){
    startingLocationsBoard = new EditorBoard();
  }

  //Editor Frontend calls this method, passing in dimensions of board set by user
  public ChangeBoardSize(int width, int height){
    startingLocationsBoard.setSize(width, height);
  }
}

public class EditorBoard{
  private List<ArrayList<EditorTile>> board;

  public EditorBoard(){
    board = new ArrayList<ArrayList<EditorTile>>();
    //fills board of default size with tiles that the board "owns"
    board.initializeTiles();
  }

  public void setSize(int width, int height){
    //removes or adds tiles from board depending on new board dimensions
  }


}