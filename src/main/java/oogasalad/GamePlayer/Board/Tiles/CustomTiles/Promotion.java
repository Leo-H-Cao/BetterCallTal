package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.Frontend.Game.GameView.promotionPopUp;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Promotion implements TileAction {

  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    List<Piece> pieceList = tile.getPieces().stream()
        .filter(p -> {
          return p.toString().equals("pawn");
             //p.isPromotable()
        })
        .collect(Collectors.toList());
    Piece p = promotionPopUp(pieceList);

    return null;

  }
  //TODO ASK ABOUT PROMOTABLE ATTRIBUTE
}
