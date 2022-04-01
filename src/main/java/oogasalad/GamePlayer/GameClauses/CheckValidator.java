package oogasalad.GamePlayer.GameClauses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.MovementModifier;

/**
 * @author Jose Santillan
 * @author Jed Yang
 */
public class CheckValidator implements MovementModifier {

  public boolean isInCheck(ChessBoard board, int id) {
    Set<Player> playerList = new HashSet<>(Set.of(board.getPlayers()));

    playerList = playerList.stream()
        .filter(player -> player.teamID() != id)
        .collect(Collectors.toSet());




    return false;

  }





  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    return null;
  }
}
