package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;

public class BankLeaver implements MovementInterface {

  /***
   * Creates a custom movement that allows pieces to leave the bank
   */
  public BankLeaver() {

  }

  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    return null;
  }

  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    return null;
  }

  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return null;
  }

  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return null;
  }

  @Override
  public List<Coordinate> getRelativeCoords() {
    return null;
  }
}
