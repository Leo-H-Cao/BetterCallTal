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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demote implements TileAction {

  private static final Logger LOG = LogManager.getLogger(Demote.class);
  private static final String DEMOTE_FILE_PATH = "doc/GameEngineResources/Other/DemotionPiece";
  private static final String DEMOTE_PIECE_NAME = getDemotablePieceName();

  /**
   * Reads in promotable piece names from a text file
   *
   * @return promotable piece list
   */
  private static String getDemotablePieceName() {
    try {
      File demoteFile = new File(DEMOTE_FILE_PATH);
      Scanner reader = new Scanner(demoteFile);
      String demoteName = reader.next();
      reader.close();
      return demoteName;
    } catch (Exception e) {
      return "Pawn";
    }
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
        p -> p.getName().equals(DEMOTE_PIECE_NAME)).findFirst().orElse(tile.getPiece().get());
    Piece clone = demotePiece.clone();
    tile.clearPieces();

    try {clone.updateCoordinates(tile, board); }
    catch (OutsideOfBoardException e) {LOG.warn("Placing piece after demotion failed.");}

    return Set.of(tile);
  }
}
