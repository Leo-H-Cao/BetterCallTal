package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.Frontend.Game.GameView.promotionPopUp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Creates a tile action that promotes the given piece on it
 *
 * @author Vincent Chen
 */
public class Promotion implements TileAction {

  private static final Logger LOG = LogManager.getLogger(Promotion.class);
  private static final String PROMOTABLE_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_PROMOTABLE_FILE_PATH = "Promotable";
  private static final List<String> DEFAULT_PROMOTABLE = List.of("Pawn");

  private List<String> promotablePieceNames;

  /***
   * Creates Promotion with default config file
   */
  public Promotion() {
    this(DEFAULT_PROMOTABLE_FILE_PATH);
  }

  /***
   * Creates Promotion with given config file
   *
   * @param configFile to read
   */
  public Promotion(String configFile) {
    promotablePieceNames = FileReader.readManyStrings(PROMOTABLE_FILE_PATH_HEADER + configFile, DEFAULT_PROMOTABLE);
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
    LOG.debug(String.format("In promotion: %s", tile.getPiece().get().getName()));
    if(tile.getPiece().isEmpty()) {return Collections.emptySet();}
    if(promotablePieceNames.stream().noneMatch(n ->
        n.equalsIgnoreCase(tile.getPiece().get().getName()))) return Collections.emptySet();
    List<Piece> pieceList = board.getPieceList().get(tile.getPiece().get().getTeam()).stream().
        filter(p -> !p.isTargetPiece() &&
            !p.getName().equalsIgnoreCase(tile.getPiece().get().getName())).collect(Collectors.toList());
    Piece p = pieceList.size() == 1 ? pieceList.get(0) : promotionPopUp(pieceList);
    Piece clone = p.clone();
    tile.clearPieces();
    try {clone.updateCoordinates(tile, board); }
    catch (OutsideOfBoardException e) {LOG.warn("Placing piece after promotion failed.");}

    return Set.of(tile);
  }
}
