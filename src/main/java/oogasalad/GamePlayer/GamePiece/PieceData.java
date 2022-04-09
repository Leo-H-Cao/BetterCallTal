package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;

public record PieceData(Coordinate startingLocation,
                        String name,
                        double pointValue,
                        int team,
                        boolean mainPiece,
                        List<MovementInterface> movements,
                        List<MovementInterface> captures,
                        List<MovementModifier> movementModifiers,
                        List<MovementModifier> onInteractionModifiers,
                        String img) {
  @Override
  public String toString() {
    return String.format("%s: (%d, %d)", name, startingLocation.getRow(), startingLocation.getCol()) + "; " + movements;
  }
}
