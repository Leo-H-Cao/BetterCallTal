package oogasalad.Editor.ModelState.BoardState;

/**
 * Enums representing the different tile modifiers that are allowed
 * @author Leo Cao
 */
public enum TileEffect {
  NONE("none"),
  BLACKHOLE("BlackHoleAction"),
  PROMOTION("Promotion"),
  SWAP("Swap"),
  PROMOTIONREVERSE("PromotionReverse"),
  DEMOTE("Demote"),
  FIRE("Fire");

  // Member to hold the name
  private String string;

  // constructor to set the string
  TileEffect(String name){string = name;}

  @Override
  public String toString() {
    return string;
  }
}
