package oogasalad.GamePlayer.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 * Abstraction of movements that follow the "basic" pattern: a collection of relative coordinates
 * and infinite/not infinite extension.
 *
 * @author Vincent Chen
 */
public abstract class  BasicMovementInterface implements MovementInterface {

  private List<Coordinate> possibleMoves;
  private boolean infinite;

  /***
   * Creates a class representing a basic piece movement
   */
  public BasicMovementInterface(List<Coordinate> possibleMoves, boolean infinite) {
    this.possibleMoves = possibleMoves;
    this.infinite = infinite;
  }

  /***
   * Creates a class representing a basic piece movement with one coordinate provided
   */
  public BasicMovementInterface(Coordinate possibleMove, boolean infinite) {
    this(List.of(possibleMove), infinite);
  }

  /**
   * Constructor for Jackson serialization and deserialization
   */
  public BasicMovementInterface(){
    this.possibleMoves = new ArrayList<>();
    this.infinite = false;
  }

  /***
   * @return possible moves list for subclasses to use
   */
  protected List<Coordinate> getPossibleMoves() {
    return possibleMoves;
  }

  /***
   * @return isInfinite for subclasses to use
   */
  public  boolean isInfinite() {
    return infinite;
  }

  /***
   * @return relative coordinates for a basic movement
   */
  public List<Coordinate> getRelativeCoords() {
    return possibleMoves;
  }

  /***
   * @return equal if both movements have the same possible moves and infinite is the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicMovementInterface movement = (BasicMovementInterface) o;
    return infinite == movement.infinite && Objects.equals(possibleMoves,
        movement.possibleMoves);
  }

  /***
   * @return hash of this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(possibleMoves, infinite);
  }

  /***
   * @return String of all relative coordinates
   */
  public String toString() {
    return possibleMoves.toString() + ": " + infinite;
  }
}
