package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;

public record SupplementaryPieceData(String name,
                                     double pointValue,
                                     int team,
                                     boolean mainPiece,
                                     String img) {
}
