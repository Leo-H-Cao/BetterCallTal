package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.io.File;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Promotion but it just adds the reverse of possible moves
 */
public class PromotionReverse implements TileAction{

  private static final String PROMOTION_IMAGE_FILE_PATH = "doc/GameEngineResources/Other/PromotionReverseNewImage";
  private static final Logger LOG = LogManager.getLogger(PromotionReverse.class);
  private static final String DEFAULT_PROMOTION_IMAGE = "King";

  private static final String PROMOTION_IMAGE = getPromotionImage();

  /***
   * Assigns piece immune to explosions, default is just pawn
   */
  private static String getPromotionImage() {

    try {
      File immuneFile = new File(PROMOTION_IMAGE_FILE_PATH);
      Scanner reader = new Scanner(immuneFile);
      String newImage = reader.next();
      reader.close();
      return newImage;
    } catch (Exception e) {
      LOG.warn("Could not find promotion reverse image file");
      return DEFAULT_PROMOTION_IMAGE;
    }
  }

  /**
   * Adds the reverse of all moves and captures to the given piece
   *
   * @return tile parameter, only tile that is updated
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    if(tile.getPiece().isEmpty()) return Collections.emptySet();

    Piece promotedPiece = tile.getPiece().get();
    promotedPiece.addNewMovements(Movement.invertMovements(promotedPiece.getMoves()),
        Movement.invertMovements(promotedPiece.getCaptures()));
    //FIXME: because of current frontend implementation, name change determines image file change
    promotedPiece.updateName(PROMOTION_IMAGE);
    return Set.of(tile);
  }
}
