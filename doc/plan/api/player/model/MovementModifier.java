package oogasalad;

public interface MovementModifier {

  /***
   * Modifies a set of movements based on some rule, such as check. All the moves that keep the
   * king in check would be removed.
   *
   * @param movementSet to modify
   * @return movementSet modified to fit rule determined by the exact implementation
   */
  Set<Movement> modifyMovement(Piece piece, Set<Movement> movementSet);
}
