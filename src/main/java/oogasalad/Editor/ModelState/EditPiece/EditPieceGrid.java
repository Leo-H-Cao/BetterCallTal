package oogasalad.Editor.ModelState.EditPiece;

public class EditPieceGrid {
  private final int PIECE_GRID_SIZE = 7;

  private PieceGridTile[][] pieceGrid;

  public EditPieceGrid(){
    pieceGrid = new PieceGridTile[PIECE_GRID_SIZE][PIECE_GRID_SIZE];
    initializePieceBoard();

  }

  public void setTileOpen(int x, int y){
    pieceGrid[y][x] = PieceGridTile.OPEN;
  }

  public void setTileClosed(int x, int y){
    pieceGrid[y][x] = PieceGridTile.CLOSED;
  }

  public void setTileInfinite(int dirX, int dirY){
    if(dirX == 1 && dirY == 0){
      int row = 3;
      for(int i = 4; i < PIECE_GRID_SIZE; i++){
        pieceGrid[row][i] = PieceGridTile.OPEN;
        if(i == PIECE_GRID_SIZE - 1){
          pieceGrid[row][i] = PieceGridTile.INFINITY;
        }
      }
    }
    else if(dirX == -1 && dirY == 0){
      int row = 3;
      for(int i = 2; i >= 0;i--){
        pieceGrid[row][i] = PieceGridTile.OPEN;
        if(i == 0){
          pieceGrid[row][i] = PieceGridTile.INFINITY;
        }
      }
    }
    else if(dirX == 0 || dirY == 0){
      int row = 3-dirY;
      int col = 3+dirX;
      while(row > 0 && row < PIECE_GRID_SIZE-1 && col > 0 && col < PIECE_GRID_SIZE-1){
        pieceGrid[row][col] = PieceGridTile.OPEN;
        row -= dirY;
        col += dirX;
      }
      pieceGrid[row][col] = PieceGridTile.INFINITY;
    }
    else if(dirX == 0 && dirY == 1){
      int column = 3;
      for(int i = 2; i >= 0; i--){
        pieceGrid[i][column] = PieceGridTile.OPEN;
        if(i == 0){
          pieceGrid[i][column] = PieceGridTile.INFINITY;
        }
      }
    }
    else if(dirX == 0 && dirY == -1){
      int column = 3;
      for(int i = 4; i < PIECE_GRID_SIZE; i++){
        pieceGrid[i][column] = PieceGridTile.OPEN;
        if(i == PIECE_GRID_SIZE - 1){
          pieceGrid[i][column] = PieceGridTile.INFINITY;
        }
      }
    }
    //diagonal
    else{
      int col = 3 + dirX;
      int row = 3 - dirY;
      while(row > 0 && row < PIECE_GRID_SIZE-1){
        pieceGrid[row][col] = PieceGridTile.OPEN;
        row -= dirY;
        col += dirX;
      }
      pieceGrid[row][col] = PieceGridTile.INFINITY;
    }
  }

  private void initializePieceBoard(){
    for(int i = 0; i < PIECE_GRID_SIZE; i++){
      for(int j = 0; j < PIECE_GRID_SIZE; j++){
        pieceGrid[i][j] = PieceGridTile.CLOSED;
      }
    }
  }

}
