package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

/***
 * BankLeaver where the piece is not removed from the bank
 *
 * @author Vincent Chen
 */
public class BankLeaverNoRemove extends BankLeaver {

  private static final String TTT_DEFAULT_FILE = "TicTacToeConfig";
  /***
   * Create BankLeaverNoRemove with default config file
   */
  public BankLeaverNoRemove() {
    super(TTT_DEFAULT_FILE);
  }

  /***
   * Create BankLeaverNoRemove with given config file
   *
   * @param configFile to read
   */
  public BankLeaverNoRemove(String configFile) {
    super(configFile);
  }

  /**
   * @return updated squares when a piece leaves the bank - a copy is made to stay in the bank
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    Piece clone = piece.clone();
    Coordinate oldCoordinate = piece.getCoordinates();
    Set<ChessTile> updatedSquares = super.movePiece(piece, finalSquare, board);
    board.getTile(oldCoordinate).addPiece(clone);
    return updatedSquares;
  }
}
