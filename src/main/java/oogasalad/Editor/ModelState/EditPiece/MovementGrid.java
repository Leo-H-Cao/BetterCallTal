package oogasalad.Editor.ModelState.EditPiece;

import oogasalad.Editor.Exceptions.MovementGridException;

public class MovementGrid {
  public static final int PIECE_GRID_SIZE = 7;
  private final int PIECE_LOC_X = 3;
  private final int PIECE_LOC_Y = 3;
  private final String INVALID_INFINITE_MOVEMENT_ERR = "Directions for infinite movement are invalid";
  private final String INVALID_FINITE_MOVEMENT_ERR = "Coordinates for finite movement are invalid";

  private PieceGridTile[][] pieceGrid;

  public MovementGrid(){
    pieceGrid = new PieceGridTile[PIECE_GRID_SIZE][PIECE_GRID_SIZE];
    initializePieceBoard();
  }

  public void setTileOpen(int x, int y){
    checkCoordinates(x, y);
    pieceGrid[y][x] = PieceGridTile.OPEN;
  }

  public void setTileClosed(int x, int y){
    checkCoordinates(x, y);
    pieceGrid[y][x] = PieceGridTile.CLOSED;
  }

  public void setTileInfinite(int dirX, int dirY){
    checkInfiniteDirections(dirX, dirY);
    int row = PIECE_LOC_X-dirY;
    int col = PIECE_LOC_Y+dirX;
    while(row > 0 && row < PIECE_GRID_SIZE-1 && col > 0 && col < PIECE_GRID_SIZE-1){
      pieceGrid[row][col] = PieceGridTile.OPEN;
      row -= dirY;
      col += dirX;
    }
    pieceGrid[row][col] = PieceGridTile.INFINITY;
  }

  public void removeTileInfinite(int dirX, int dirY){
    checkInfiniteDirections(dirX, dirY);
    int row = PIECE_LOC_X-dirY;
    int col = PIECE_LOC_Y+dirX;
    while(row >= 0 && row < PIECE_GRID_SIZE && col >= 0 && col < PIECE_GRID_SIZE){
      pieceGrid[row][col] = PieceGridTile.CLOSED;
      row -= dirY;
      col += dirX;
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

  private void checkCoordinates(int x, int y){
    if(x >= PIECE_GRID_SIZE || x < 0 || y >= PIECE_GRID_SIZE || y < 0 || (x==PIECE_LOC_X && y==PIECE_LOC_Y)){
      throw new MovementGridException(INVALID_FINITE_MOVEMENT_ERR);
    }
  }

  private void checkInfiniteDirections(int dirX, int dirY){
    if((dirX != 0 && dirX != 1 && dirX !=-1) || (dirY != 0 && dirY != 1 && dirY !=-1)){
      throw new MovementGridException(INVALID_INFINITE_MOVEMENT_ERR);
    }
  }

}
