package oogasalad.Editor.ModelState.BoardState;

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
