package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.Frontend.Game.GameView.promotionPopUp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.MovementModifiers.Atomic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Creates a tile action that promotes the given piece on it
 */
public class Promotion implements TileAction {

  private static final Logger LOG = LogManager.getLogger(Promotion.class);
  private static final String PROMOTABLE_FILE_PATH = "doc/GameEngineResources/Other/Promotable";
  private static final List<String> PROMOTABLE_PIECE_NAMES = getPromotablePieceNames();

  /**
   * Reads in promotable piece names from a text file
   *
   * @return promotable piece list
   */
  private static List<String> getPromotablePieceNames() {
    ArrayList<String> nameList = new ArrayList<>();
    try {
      File immuneFile = new File(PROMOTABLE_FILE_PATH);
      Scanner reader = new Scanner(immuneFile);
      while (reader.hasNext()) {
        nameList.add(reader.next().trim());
      }
      reader.close();
      return nameList;
    } catch (Exception e) {
      return List.of("Pawn");
    }
  }

  /**
   * Promotes a piece if applicable
   *
   * @param tile is the promotion square
   * @param board to promote on
   * @return tile, which has changed due to the promotion
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
//    LOG.debug(String.format("Promotion pieces: %s", PROMOTABLE_PIECE_NAMES));
    LOG.debug(String.format("In promotion: %s", tile.getPiece().get().getName()));
    if(tile.getPiece().isEmpty()) return Collections.emptySet();
    if(PROMOTABLE_PIECE_NAMES.stream().noneMatch(n ->
        n.equalsIgnoreCase(tile.getPiece().get().getName()))) return Collections.emptySet();
    List<Piece> pieceList = board.getPieceList().get(tile.getPiece().get().getTeam()).stream().
        filter((p) -> !p.isTargetPiece() &&
            !p.getName().equalsIgnoreCase(tile.getPiece().get().getName())).collect(Collectors.toList());
    Piece p = promotionPopUp(pieceList);
    Piece clone = p.clone();
    tile.clearPieces();
    
    try {clone.updateCoordinates(tile, board); }
    catch (OutsideOfBoardException e) {LOG.warn("Placing piece after promotion failed.");}

    return Set.of(tile);
  }
}
