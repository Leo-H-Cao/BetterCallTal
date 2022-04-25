package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Creates a tile action that demotes a piece to a given piece
 *
 * @author Vincent Chen
 */
public class Demote implements TileAction {

  private static final Logger LOG = LogManager.getLogger(Demote.class);
  private static final String DEMOTE_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_DEMOTE_FILE = "DemotionPiece";
  private static final List<String> DEFAULT_DEMOTE_PIECE = List.of("Pawn");

  private String demotePieceName;

  /***
   * Create Demote with default config file
   */
  public Demote() {
    this(DEFAULT_DEMOTE_FILE);
  }

  /***
   * Create Demote with given config file
   *
   * @param demoteFilePath to read
   */
  public Demote(String demoteFilePath) {
    demotePieceName = FileReader.readManyStrings(DEMOTE_FILE_PATH_HEADER + demoteFilePath, DEFAULT_DEMOTE_PIECE).get(0);
  }

  /***
   * Demotes a piece to the given demotion piece in the file
   *
   * @return this tile
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    if(tile.getPiece().isEmpty()) return Collections.emptySet();
    Piece demotePiece = board.getPieceList().get(tile.getPiece().get().getTeam()).stream().filter(
        p -> p.getName().equals(demotePieceName)).findFirst().orElse(tile.getPiece().get());
    Piece clone = demotePiece.clone();
    clone.forceUpdateCoordinates(tile.getCoordinates());
    LOG.debug(String.format("Current piece on tile: %s", tile.getPiece().get()));
    tile.clearPieces();
    board.placePiece(tile.getCoordinates(), clone);

    return Set.of(tile);
  }
}
