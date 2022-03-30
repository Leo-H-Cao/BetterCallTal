package oogasalad.GamePlayer.Board;

import java.util.Set;

public record TurnUpdate(Set<ChessTile> updatedSquares, int nextPlayer) {

}
