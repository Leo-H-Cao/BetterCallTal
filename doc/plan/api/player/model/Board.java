package oogasalad;

public interface Board {

  /***
   * Moves the piece on fromSquare to toSquare
   *
   * @param piece to move
   * @param toSquare end square
   * @return set of updated tiles
   */
  Set<Tile> move(Piece piece, Tile toSquare);

  /***
   * retrieves the current state of the Board
   * @return
   */
  Board retrieveState();
}
