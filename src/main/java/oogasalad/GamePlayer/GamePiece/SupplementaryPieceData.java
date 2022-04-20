package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;

/***
 * Piece data that does not change
 *
 * @author Vincent Chen
 */
public record SupplementaryPieceData(double pointValue,
                                     boolean mainPiece) {}
