package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifier;
import oogasalad.GamePlayer.Movement.MovementSetModifier;

public record PieceData(Coordinate startingLocation,
                        String name,
                        double pointValue,
                        int team,
                        boolean mainPiece,
                        List<Movement> movements,
                        List<Movement> captures,
                        List<MovementSetModifier> movementSetModifiers,
                        List<MovementModifier> movementModifiers,
                        List<MovementModifier> onInteractionModifiers,
                        String img) {

}
