package oogasalad.GamePlayer.Movement.MovementModifiers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/***
 * Abstraction that allows functionality to happen after a move is made
 *
 * @author Vincent Chen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({@JsonSubTypes.Type(value = Atomic.class, name = "Atomic"),
    @JsonSubTypes.Type(value = BankJoiner.class, name = "BankJoiner"),
    @JsonSubTypes.Type(value = GravityMovementModifier.class, name = "GravityMovementModifier"),
    @JsonSubTypes.Type(value = MoveAbsorption.class, name = "MoveAbsorption"),
    @JsonSubTypes.Type(value = RandomDisappear.class, name = "RandomDisappear")})
public interface MovementModifier {

  /***
   * Modifies a movement based on rules such as check
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return Set of updated tiles after movement is modified
   */
  Set<ChessTile> updateMovement(Piece piece, ChessBoard board);
}
