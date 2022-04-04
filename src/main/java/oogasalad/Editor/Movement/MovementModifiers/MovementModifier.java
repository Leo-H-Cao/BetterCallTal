<<<<<<< HEAD:src/main/java/oogasalad/Frontend/GamePlayer/Movement/MovementModifier.java
package oogasalad.Frontend.GamePlayer.Movement;
=======
package oogasalad.Editor.Movement.MovementModifiers;
>>>>>>> 13bbc0787cf1e68b3a5d1149146123c54d282f81:src/main/java/oogasalad/Editor/Movement/MovementModifiers/MovementModifier.java

import java.util.Set;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;

public interface MovementModifier {

  /***
   * Modifies a movement based on rules such as check
   *
   * @param piece that is referenced
   * @param finalTile the tile the piece is moving to
   * @param board that piece is on
   * @return Set of updated tiles after movement is modified
   */
  Set<ChessTile> updateMovement(Piece piece, ChessTile finalTile, ChessBoard board);
}
