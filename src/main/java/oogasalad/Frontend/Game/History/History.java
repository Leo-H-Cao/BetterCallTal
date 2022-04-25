package oogasalad.Frontend.Game.History;

import oogasalad.GamePlayer.Board.ChessBoard;

public interface History {

  ChessBoard getBoard();

  void add(ChessBoard board);

  History next();

  History previous();
}
