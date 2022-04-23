package oogasalad.GamePlayer.Movement.MovementModifiers;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.util.FileReader;

/***
 * Piece has the given chance of disappearing after a move
 *
 * @author Vincent Chen
 */
public class RandomDisappear implements MovementModifier {

  private static final String RD_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String RD_DEFAULT_FILE = "RDHalf";
  private static final double DEFAULT_RD = 0.5;

  private double chanceOfDisappearing;

  /***
   * Creates RandomDisappear with default config file
   */
  public RandomDisappear() {
    this(RD_DEFAULT_FILE);
  }

  /***
   * Creates RandomDisappear with given config file
   *
   * @param configFile to read
   */
  public RandomDisappear(String configFile) {
    chanceOfDisappearing = FileReader.readOneDouble(
        RD_FILE_PATH_HEADER + RD_DEFAULT_FILE, DEFAULT_RD);
  }

  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    if (Math.random() <= chanceOfDisappearing) {
      try {
        board.getTile(piece.getCoordinates()).removePiece(piece);
        return Set.of(board.getTile(piece.getCoordinates()));
      } catch (OutsideOfBoardException e) {return Collections.emptySet();}
    }
    return Collections.emptySet();
  }
}
