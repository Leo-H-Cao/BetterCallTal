package oogasalad;

public interface Board {

  /***
   * Moves the piece on fromSquare to toSquare
   *
   * @param fromSquare origin square
   * @param toSquare end square
   * @return if move can be done
   */
  boolean move(Tile fromSquare, Tile toSquare);
}
