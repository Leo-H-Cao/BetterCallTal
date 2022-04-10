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
    if(dirX == 0 && dirY == 0){
      return;
    }
    int row = 3-dirY;
    int col = 3+dirX;
    while(row > 0 && row < PIECE_GRID_SIZE-1 && col > 0 && col < PIECE_GRID_SIZE-1){
      pieceGrid[row][col] = PieceGridTile.OPEN;
      row -= dirY;
      col += dirX;
    }
    pieceGrid[row][col] = PieceGridTile.INFINITY;
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
    pieceGrid[3][3] = PieceGridTile.PIECE;
  }

}
