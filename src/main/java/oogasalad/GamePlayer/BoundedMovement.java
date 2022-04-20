package oogasalad.GamePlayer;

import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;

@Deprecated
public interface BoundedMovement {



  void setBoundedMovementRules(List<Coordinate> allowedMovement);

}
