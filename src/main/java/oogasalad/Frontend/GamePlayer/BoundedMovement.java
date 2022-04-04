package oogasalad.Frontend.GamePlayer;

import java.util.List;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;

public interface BoundedMovement {



  void setBoundedMovementRules(List<Coordinate> allowedMovement);

}
