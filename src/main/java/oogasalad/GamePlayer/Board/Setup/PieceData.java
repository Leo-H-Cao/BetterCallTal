package oogasalad.GamePlayer.Board.Setup;

import java.util.List;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;

/***
 * Data used to create a piece generated from a JSON
 *
 * @author Vincent Chen
 */
public record PieceData(String pieceName, String imgFile, int pointValue,
                        List<MovementInterface> basicMovements,
                        List<MovementInterface> basicCaptures, List<MovementInterface> customMoves,
                        List<MovementModifier>
                            movementModifiers, List<MovementModifier> onInteractionModifiers) {

}
