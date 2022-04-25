package oogasalad.Editor.ModelState.EditPiece;

import oogasalad.Editor.Exceptions.MovementGridException;
import oogasalad.GamePlayer.Movement.Coordinate;

/**
 * Movement grid representing valid movements and captures for the piece that it belongs to
 * @author Leo Cao
 */
public class MovementGrid {
  public static final int PIECE_GRID_SIZE = 7;
  private final int PIECE_LOC_X = 3;
  private final int PIECE_LOC_Y = 3;

	private final PieceGridTile[][] pieceGrid;

  public MovementGrid(){
    pieceGrid = new PieceGridTile[PIECE_GRID_SIZE][PIECE_GRID_SIZE];
    initializePieceBoard();
  }

  /**
   * Sets movement grid tile as open, closed, or capture for movement
   * @param x coord of tile
   * @param y coord of tile
   * @param tileStatus determine whether tile is closed or open for movement/capture
   */
  public void setTile(int x, int y, PieceGridTile tileStatus){
    checkCoordinates(x, y);
    pieceGrid[y][x] = tileStatus;
  }

  public PieceGridTile getTileStatus(int x, int y){
    return pieceGrid[y][x];
  }

  private void initializePieceBoard(){
    for(int i = 0; i < PIECE_GRID_SIZE; i++){
      for(int j = 0; j < PIECE_GRID_SIZE; j++){
        pieceGrid[i][j] = PieceGridTile.CLOSED;
      }
    }
    pieceGrid[PIECE_LOC_X][PIECE_LOC_Y] = PieceGridTile.PIECE;
  }

  public Coordinate getPieceLocation() {
    return new Coordinate(PIECE_LOC_X, PIECE_LOC_Y);
  }

  private void checkCoordinates(int x, int y){
    if(x >= PIECE_GRID_SIZE || x < 0 || y >= PIECE_GRID_SIZE || y < 0){
	    String INVALID_MOVEMENT_ERR = "Coordinates for movement are invalid";
	    throw new MovementGridException(INVALID_MOVEMENT_ERR);
    } else if((x==PIECE_LOC_X && y==PIECE_LOC_Y)) {
	    String INVALID_PIECE_CHANGE = "Piece tile cannot be changed.";
	    throw new MovementGridException(INVALID_PIECE_CHANGE);
    }
  }

}
