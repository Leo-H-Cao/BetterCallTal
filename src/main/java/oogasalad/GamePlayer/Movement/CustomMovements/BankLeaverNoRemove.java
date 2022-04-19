package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_CONFIG_FILE_HEADER;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_DEFAULT_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.ValidStateChecker.BankBlocker;
import org.json.JSONObject;

/***
 * BankLeaver
 */
public class BankLeaverNoRemove extends BankLeaver {

  /***
   * Create BankLeaverNoRemove with default config file
   */
  public BankLeaverNoRemove() {
    super(CH_DEFAULT_FILE);
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
