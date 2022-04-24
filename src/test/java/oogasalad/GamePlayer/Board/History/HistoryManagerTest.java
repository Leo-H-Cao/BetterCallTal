package oogasalad.GamePlayer.Board.History;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Server.SessionManager;
import oogasalad.Server.ServerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoryManagerTest {

  private History board0;
  private History board1;
  private History board2;
  private History board3;
  private History board4;


  private History generateHistory(int num) {
    int row = num / 3;
    int col = num % 3;
    Player playerOne = new Player(0, null);
    Player[] players = new Player[]{playerOne};

    TurnCriteria turnCriteria = new Linear(players);

    ChessBoard board = new ChessBoard(3, 3, turnCriteria, players, List.of());
    Piece piece = new Piece(new PieceData(new Coordinate(row, col), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), "test1.png"));
    List<Piece> pieces = List.of(piece);
    board.setPieces(pieces);
    return new History(board, Set.of(piece), Set.of(new ChessTile(new Coordinate(row, col))));
  }

  @BeforeEach
  void setUp() {
    board0 = generateHistory(0);
    board1 = generateHistory(1);
    board2 = generateHistory(2);
    board3 = generateHistory(3);
    board4 = generateHistory(4);
  }

  @Test
  void standardLocalGameTest() {
    HistoryManager historyManager = new LocalHistoryManager();
    standardGameTest(historyManager);
  }

  @Test
  void standardRemoteGameTest() {
    SessionManager sessionManager = new SessionManager();
    try {
      sessionManager.createGameSession("test", 0,1,board0.board());
    } catch (Exception e) {
      fail("Could not create session");
    }
    HistoryManager historyManager = new RemoteHistoryManager("test");
    standardGameTest(historyManager);
  }

  private void standardGameTest(HistoryManager historyManager) {
    historyManager.add(board0);
    assertEquals(1, historyManager.size());
    historyManager.add(board1);
    assertEquals(2, historyManager.size());
    historyManager.add(board2);
    assertEquals(3, historyManager.size());
    historyManager.add(board3);
    assertEquals(4, historyManager.size());
    assertEquals(3, historyManager.getCurrentIndex());
    assertEquals(historyManager.get(2), board2);
    assertEquals(historyManager.getCurrent(), board3);
    assertEquals(historyManager.getFirst(), board0);
    assertEquals(historyManager.getCurrentBoard(), board3.board());
    assertEquals(historyManager.getCurrent(), historyManager.getLast());
    historyManager.clearHistory();
    assertTrue(historyManager.isEmpty());
    assertEquals(0, historyManager.getCurrentIndex());
  }

  @Test
  void undoLocalGameTest() {
    HistoryManager historyManager = new LocalHistoryManager();
    undoGameTest(historyManager);
  }

  private void undoGameTest(HistoryManager historyManager) {
    historyManager.add(board0);
    historyManager.add(board1);
    historyManager.add(board2);
    historyManager.add(board3);
    assertEquals(4, historyManager.size());
    historyManager.goToState(1);
    assertEquals(1, historyManager.getCurrentIndex());
    assertEquals(historyManager.getCurrent(), board1);
    assertEquals(historyManager.getCurrentBoard(), board1.board());
    assertEquals(historyManager.get(2), board2);
    assertEquals(historyManager.getLast(), board3);
    assertEquals(historyManager.getFirst(), board0);
    historyManager.goToState(3);
    assertEquals(4, historyManager.size());
    assertEquals(3, historyManager.getCurrentIndex());
    assertEquals(historyManager.getCurrent(), board3);
    assertEquals(historyManager.getCurrentBoard(), board3.board());
    assertEquals(historyManager.get(2), board2);
    assertEquals(historyManager.getLast(), board3);
    assertEquals(historyManager.getFirst(), board0);
    historyManager.goToState(1);
    historyManager.add(board4);
    assertEquals(3, historyManager.size());
    assertEquals(2, historyManager.getCurrentIndex());
    assertEquals(historyManager.getCurrent(), board4);
    assertEquals(historyManager.getCurrentBoard(), board4.board());
    assertEquals(historyManager.getLast(), board4);
    assertEquals(historyManager.getFirst(), board0);
  }

  @Test
  void badTest() {
    HistoryManager historyManager = new LocalHistoryManager();
    badTest(historyManager);
  }

  private void badTest(HistoryManager historyManager) {
    historyManager.add(board0);
    historyManager.add(board1);
    historyManager.add(board2);
    historyManager.add(board3);
    assertEquals(board3, historyManager.get(100));
    assertEquals(board0, historyManager.get(-1));
  }

}