package oogasalad.GamePlayer.Movement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.CustomMovements.BankLeaver;
import oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverNoRemove;
import oogasalad.GamePlayer.Movement.CustomMovements.Castling;
import oogasalad.GamePlayer.Movement.CustomMovements.CheckersCapture;
import oogasalad.GamePlayer.Movement.CustomMovements.DoubleFirstMove;
import oogasalad.GamePlayer.Movement.CustomMovements.EnPassant;
import oogasalad.GamePlayer.Movement.CustomMovements.LoopAround;

/***
 * Abstraction of different movement types
 *
 * @author Vincent Chen
 */
public interface MovementInterface {

  /**
   * Moves the piece on fromSquare to finalSquare
   *
   * @param piece       to move
   * @param finalSquare end square
   * @param board       to move on
   * @return set of updated tiles
   * @throws InvalidMoveException    if the piece cannot move to the given square
   * @throws OutsideOfBoardException if the provided square is outside the board
   */
  Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException;

  /***
   * Captures piece on captureSquare
   *
   * @param piece to move
   * @param captureSquare end square
   * @param board to move on
   * @return set of updated tiles
   * @throws InvalidMoveException if the piece cannot move to the given square
   */
  Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException;

  /***
   * Returns all possible captures a piece can make
   *
   * @param piece to get captures from
   * @param board to move on
   * @return set of tiles the piece can capture on
   */
  Set<ChessTile> getCaptures(Piece piece, ChessBoard board);

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @param board to move on
   * @return set of tiles the piece can move to
   */
  Set<ChessTile> getMoves(Piece piece, ChessBoard board);

  /***
   * @return relative coordinates if applicable
   */
  List<Coordinate> getRelativeCoords();
}
