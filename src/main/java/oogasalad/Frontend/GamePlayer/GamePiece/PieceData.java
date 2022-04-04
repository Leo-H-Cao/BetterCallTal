package oogasalad.Frontend.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.Frontend.GamePlayer.Movement.CustomMovement;
import oogasalad.Frontend.GamePlayer.Movement.Movement;
import oogasalad.Frontend.GamePlayer.Movement.MovementModifier;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;

public record PieceData(Coordinate startingLocation,
                        String name,
                        double pointValue,
                        int team,
                        boolean mainPiece,
                        List<Movement> movements,
                        List<Movement> captures,
                        List<CustomMovement> movementSetModifiers,
                        List<MovementModifier> movementModifiers,
                        List<MovementModifier> onInteractionModifiers,
                        String img) {

}
