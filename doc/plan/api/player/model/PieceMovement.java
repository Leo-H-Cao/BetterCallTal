public interface PieceMovement{
  public void movePiece(Piece piece, Tile finalSquare);

  public Set<Tile> getMoves(Piece piece);
}