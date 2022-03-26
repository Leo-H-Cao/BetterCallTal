package oogasalad;

public class Board {

  public boolean move(Piece piece, Tile toSquare) {
    Set<Tile> movements = piece.getMoves();
    if(movements.contains(toSquare)) {
      return piece.move(toSquare);
    }
    return false;
  }
}

public class Piece {
  public Set<Tile> getMoves() {
    Set<Tile> destinations = new HashSet<>();
    myPieceMovements.foreach(m -> destinations.addAll(m.getMoves(this)));
    myModifiers.foreach(m -> destinations = m.modifyMovements(this, movements));
    return destinations;
  }

  public boolean move(Tile toSquare) {
    if(myCaptureCriteria.isCapture(this, toSquare)) {
      return capture(toSquare);
    }
    else {
      return movePieceAndClearCurrent();
    }
  }

  private boolean capture() {...}

  private boolean movePieceAndClearCurrent() {...}
}


