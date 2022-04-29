package oogasalad.GamePlayer.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import oogasalad.GamePlayer.Board.TurnCriteria.ConstantIncrease;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.OnlyFirstTeam;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.GamePiece.PieceData;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Server.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnTest {

  private ChessBoard board;
  private TurnCriteria turnCriteria;

  private Player playerOne;
  private Player playerTwo;
  private Player playerThree;
  private Player[] players;

  private Piece pieceOne;
  private Piece pieceTwo;
  private Piece pieceThree;
  private List<Piece> pieces;
  private GamePlayers gamePlayers;
  private SessionManager sessionManager;

  @BeforeEach
  void setUp() {
    playerOne = new Player(0, null);
    playerTwo = new Player(1, null);
    playerThree = new Player(2, null);
    gamePlayers = new GamePlayers(playerOne, playerTwo, playerThree);
    players = gamePlayers.getPlayersArr();
  }

  private void setBoard() {
    board = new ChessBoard(8, 8, turnCriteria, players, List.of());
    pieceOne = new Piece(new PieceData(new Coordinate(0, 0), "test1", 0, 0, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), ""));
    pieceTwo = new Piece(new PieceData(new Coordinate(1, 0), "test2", 0, 1, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), ""));
    pieceThree = new Piece(new PieceData(new Coordinate(2, 0), "test3", 0, 2, false,
        List.of(new Movement(List.of(new Coordinate(0, 1)), false)), Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), ""));
    pieces = List.of(pieceOne, pieceTwo, pieceThree);
    board.setPieces(pieces);
  }

  private void setRemoteBoard() {
    try {
      String key = "remoteboardtest";
      sessionManager = new SessionManager();
      setBoard();
      sessionManager.createGameSession(key, 0, 1, board);
      board = board.toServerChessBoard(key, 0);
    } catch (Exception e) {
      fail();
    }
  }

  private void testLinear() {
    try {
      TurnUpdate tu1 = board.move(pieceOne, new Coordinate(0, 1));
      assertEquals(tu1.nextPlayer(), 1);
      assertNotEquals("", tu1.notation());
      TurnUpdate tu2 = board.move(pieceTwo, new Coordinate(1, 1));
      assertEquals(tu2.nextPlayer(), 2);
      assertNotEquals("", tu2.notation());
      TurnUpdate tu3 = board.move(pieceThree, new Coordinate(2, 1));
      assertEquals(tu3.nextPlayer(), 0);
      assertNotEquals("", tu3.notation());
    } catch (Throwable e) {
      fail();
    }
  }

  @Test
  void testLocalLinear() {
    turnCriteria = new Linear(gamePlayers.getPlayersArr()).copy();
    setBoard();
    testLinear();
  }

  @Test
  void testRemoteLinear() {
    turnCriteria = new Linear(gamePlayers.getPlayersArr()).copy();
    try {
      setRemoteBoard();
      testLinear();
      sessionManager.endGameSession("remoteboardtest");
    } catch (Exception e) {
      fail();
    }
  }


  @Test
  void testIncreasing() {
    turnCriteria = new ConstantIncrease(gamePlayers.getPlayersArr()).copy();
    setBoard();

    try {
      assertEquals(board.move(pieceOne, new Coordinate(0, 1)).nextPlayer(), 1);
      assertEquals(board.move(pieceTwo, new Coordinate(1, 1)).nextPlayer(), 1);
      assertEquals(board.move(pieceTwo, new Coordinate(1, 2)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 1)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 2)).nextPlayer(), 2);
      assertEquals(board.move(pieceThree, new Coordinate(2, 3)).nextPlayer(), 0);
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void testLinearExceptions() {
    turnCriteria = new Linear(gamePlayers.getPlayersArr()).copy();
    setBoard();
    assertThrows(WrongPlayerException.class, () -> board.move(pieceTwo, new Coordinate(0, 1)));
    try {
      board.move(pieceOne, new Coordinate(0, 1));
    } catch (Throwable e) {
      fail();
    }

    assertThrows(WrongPlayerException.class, () -> board.move(pieceOne, new Coordinate(1, 1)));
  }

  @Test
  void testIncrExceptions() {
    turnCriteria = new ConstantIncrease(gamePlayers.getPlayersArr()).copy();
    setBoard();
    assertThrows(WrongPlayerException.class, () -> board.move(pieceTwo, new Coordinate(0, 1)));

    try {
      board.move(pieceOne, new Coordinate(0, 1));
      board.move(pieceTwo, new Coordinate(1, 1));
    } catch (Throwable e) {
      fail();
    }

    assertThrows(WrongPlayerException.class, () -> board.move(pieceOne, new Coordinate(0, 2)));
  }

  @Test
  void testOnlyFirstTeam() {
    turnCriteria = new OnlyFirstTeam(gamePlayers.getPlayersArr()).copy();
    setBoard();
    assertThrows(WrongPlayerException.class, () -> board.move(pieceTwo, new Coordinate(0, 1)));

    try {
      board.move(pieceOne, new Coordinate(0, 1));
      board.move(pieceOne, new Coordinate(0, 2));

    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void defaultConstructorTests() {
    Linear linearDefault = new Linear();
    assertEquals(0, linearDefault.getCurrentPlayer());
    linearDefault.incrementTurn();
    assertEquals(1, linearDefault.getCurrentPlayer());
    linearDefault.incrementTurn();
    assertEquals(0, linearDefault.getCurrentPlayer());

    ConstantIncrease ciDefault = new ConstantIncrease();
    assertEquals(0, ciDefault.getCurrentPlayer());
    ciDefault.incrementTurn();
    assertEquals(1, ciDefault.getCurrentPlayer());
    ciDefault.incrementTurn();
    assertEquals(1, ciDefault.getCurrentPlayer());

    OnlyFirstTeam oftDefault = new OnlyFirstTeam();
    assertEquals(0, oftDefault.getCurrentPlayer());
    oftDefault.incrementTurn();
    assertEquals(0, oftDefault.getCurrentPlayer());
    oftDefault.incrementTurn();
    assertEquals(0, oftDefault.getCurrentPlayer());
  }
}