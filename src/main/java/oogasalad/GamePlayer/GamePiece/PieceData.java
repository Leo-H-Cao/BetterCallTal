package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;

public record PieceData(Coordinate startingLocation, String name, double pointValue, int team, boolean mainPiece, List<Movement> movements, List<Movement> captures, List<MovementModifier> movementModifiers, List<MovementModifier> onInteractionModifiers) {

}
