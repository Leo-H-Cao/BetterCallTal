package oogasalad.Editor.ModelState.EditPiece;

import oogasalad.Editor.Exceptions.MovementGridException;
import oogasalad.GamePlayer.Movement.Coordinate;

public class MovementGrid {
  public static final int PIECE_GRID_SIZE = 7;
  private final int PIECE_LOC_X = 3;
  private final int PIECE_LOC_Y = 3;
  private final String INVALID_MOVEMENT_ERR = "Coordinates for movement are invalid";
  private final String INVALID_PIECE_CHANGE = "Piece tile cannot be changed.";

  private PieceGridTile[][] pieceGrid;

  public MovementGrid(){
    pieceGrid = new PieceGridTile[PIECE_GRID_SIZE][PIECE_GRID_SIZE];
    initializePieceBoard();
  }

  public void setTile(int x, int y, PieceGridTile tileStatus){
    checkCoordinates(x, y);
    if((tileStatus == PieceGridTile.CAPTURE && pieceGrid[y][x] == PieceGridTile.OPEN) ||
        (tileStatus == PieceGridTile.OPEN && pieceGrid[y][x] == PieceGridTile.CAPTURE)){
      pieceGrid[y][x] = PieceGridTile.OPENANDCAPTURE;
    }
    else{
      pieceGrid[y][x] = tileStatus;
    }
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
      throw new MovementGridException(INVALID_MOVEMENT_ERR);
    } else if((x==PIECE_LOC_X && y==PIECE_LOC_Y)) {
      throw new MovementGridException(INVALID_PIECE_CHANGE);
    }
  }

}
