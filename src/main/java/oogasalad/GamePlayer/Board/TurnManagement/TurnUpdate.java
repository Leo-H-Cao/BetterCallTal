package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

/***
 * Info relevant to the updated state of the board after a turn
 *
 * @author Vincent Chen
 */
public record TurnUpdate(Set<ChessTile> updatedSquares, int nextPlayer, String notation) {

}
