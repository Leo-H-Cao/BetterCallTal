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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Piece has the given chance of disappearing after a move
 *
 * @author Vincent Chen
 */
public class RandomDisappear implements MovementModifier {

  private static final String RD_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String RD_DEFAULT_FILE = "RDFive";
  private static final double DEFAULT_RD = 0.05;
  private static final Logger LOG = LogManager.getLogger(RandomDisappear.class);

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
        RD_FILE_PATH_HEADER + configFile, DEFAULT_RD);
    LOG.debug(String.format("Chance of disappearing: %f%%", chanceOfDisappearing * 100));
  }

  /***
   * If a randomly generated value is under the chance of disappearing, remove that piece
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return piece tile if it is removed
   */
  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    double randomVal = Math.random();
    LOG.debug(String.format("Random val: 5%f", randomVal));
    if (randomVal <= chanceOfDisappearing) {
      try {
        board.getTile(piece.getCoordinates()).removePiece(piece);
        return Set.of(board.getTile(piece.getCoordinates()));
      } catch (OutsideOfBoardException e) {return Collections.emptySet();}
    }
    return Collections.emptySet();
  }

  /***
   * @return chance of disappearing for testing
   */
  double getChanceOfDisappearing() {
    return chanceOfDisappearing;
  }
}
