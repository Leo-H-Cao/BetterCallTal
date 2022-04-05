package oogasalad.GamePlayer;

import java.util.List;
import oogasalad.Editor.Movement.Coordinate;

public interface BoundedMovement {



  void setBoundedMovementRules(List<Coordinate> allowedMovement);

}
