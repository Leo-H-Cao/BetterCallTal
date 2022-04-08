package oogasalad.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.TwoMoves;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.junit.jupiter.api.Test;

class ChessHistoryTest {


  private Map<Integer, ChessHistory> createHistory() {
    ChessHistory emptyHistory = new ChessHistory();
    ChessHistory singleHistory = new ChessHistory(List.of(createBoard(0)));
    ChessHistory twoHistory = new ChessHistory(List.of(createBoard(0), createBoard(1)));
    ChessHistory fiveHistory = new ChessHistory(
        List.of(createBoard(0), createBoard(1), createBoard(2), createBoard(3), createBoard(4)));
    return Map.of(0, emptyHistory, 1, singleHistory, 2, twoHistory, 5,
        fiveHistory);

  }

  private ChessBoard createBoard(int index) {

    Player playerOne = new Player(0, null);
    Player playerTwo = new Player(1, null);
    Player[] players = new Player[]{playerOne, playerTwo};
    Linear turnCriteria = new Linear(players);
    ChessBoard board = null;

    Piece pieceOne = new Piece(new PieceData(new Coordinate(0, index), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
    Piece pieceTwo = new Piece(new PieceData(new Coordinate(1, index), "test2", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ""));
    List<Piece> pieces = List.of(pieceOne, pieceTwo);

    board = new ChessBoard(8, 8, turnCriteria, players, List.of(new TwoMoves()));
    board.setPieces(pieces);
    return board;
  }

  private void assertEmptyVsRegular(int key, Consumer<Object> empty, Consumer<Object> regular) {
    if (key == 0) {
      empty.accept(null);
    } else {
      regular.accept(null);
    }
  }

  @Test
  void addToHistory() {
    Map<Integer, ChessHistory> historySamples = createHistory();

    // Check for normal addition
    ChessBoard toAdd = createBoard(0);
    historySamples.forEach((key, value) -> value.addToHistory(toAdd));
    historySamples.forEach((key, value) -> assertEquals(value.getLastState(), toAdd));

    // Check for null addition
    historySamples.forEach((key, value) -> value.addToHistory(null));
    historySamples.forEach((key, value) -> assertNull(value.getLastState()));
  }

  @Test
  void getHistorySize() {
    Map<Integer, ChessHistory> historySamples = createHistory();
    historySamples.forEach((key, value) -> assertEquals(value.getHistorySize(), key));
  }

  @Test
  void getState() {

  }

  @Test
  void getFirstState() {

  }

  @Test
  void getCurrentState() {
  }

  @Test
  void getLastState() {
  }

  @Test
  void getCurrentIndex() {
  }

  @Test
  void rewindBackStates() {
  }

  @Test
  void advanceForwardStates() {
  }

  @Test
  void goToState() {
  }

  @Test
  void clearHistory() {
    Map<Integer, ChessHistory> historySamples = createHistory();
    historySamples.forEach((key, value) -> assertEquals(value.clearHistory(), new ChessHistory()));
  }
}