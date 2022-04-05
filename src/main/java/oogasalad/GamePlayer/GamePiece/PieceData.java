package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.Editor.Movement.Coordinate;
import oogasalad.Editor.Movement.MovementInterface;
import oogasalad.Editor.Movement.MovementModifiers.MovementModifier;

public record PieceData(Coordinate startingLocation,
                        String name,
                        double pointValue,
                        int team,
                        boolean mainPiece,
                        List<MovementInterface> movements,
                        List<MovementInterface> captures,
                        List<MovementInterface> customMovements,
                        List<MovementModifier> movementModifiers,
                        List<MovementModifier> onInteractionModifiers,
                        String img) {

}
