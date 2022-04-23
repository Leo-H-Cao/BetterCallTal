//package oogasalad.GamePlayer.ValidStateChecker;
//
//import static oogasalad.GamePlayer.Movement.CustomMovements.BankLeaverTest.BOARD_TEST_FILES_HEADER;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//import java.util.Set;
//import oogasalad.GamePlayer.Board.ChessBoard;
//import oogasalad.GamePlayer.Board.Setup.BoardSetup;
//import oogasalad.GamePlayer.GamePiece.Piece;
//import oogasalad.GamePlayer.Movement.Coordinate;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class GravityVSCTest {
//
//  static final String GRAVITY_FILE_ONE = BOARD_TEST_FILES_HEADER + "ConnectFour.json";
//  static final String GRAVITY_FILE_TWO = BOARD_TEST_FILES_HEADER + "ConnectFourRotated.json";
//
//  private ChessBoard myBoard;
//
//  @BeforeEach
//  void setUp() {
//    try {
//      myBoard = BoardSetup.createLocalBoard(GRAVITY_FILE_ONE);
//    } catch (Exception e) {
//      fail();
//    }
//  }
//
//  @Test
//  void testConfigFiles() {
//    assertEquals(List.of(Coordinate.of(1, 0)), new GravityVSC().getRelativeCoordinates());
//    assertEquals(List.of(Coordinate.of(0, -1)), new GravityVSC("GravityC4Rotated").getRelativeCoordinates());
//    assertEquals(List.of(Coordinate.of(1, 0)), new GravityVSC("98y7f8r3").getRelativeCoordinates());
//  }
//
//  @Test
//  void testValidCheck() {
//    try {
//      Piece checkerRed = myBoard.getTile(Coordinate.of(5, 8)).getPiece().get();
//      Piece checkerBlack = myBoard.getTile(Coordinate.of(0, 8)).getPiece().get();
//      assertFalse(myBoard.getMoves(checkerRed).stream().anyMatch(t -> t.getCoordinates().getRow() < 5));
//      myBoard.move(checkerRed, Coordinate.of(5, 4));
//      assertFalse(myBoard.getMoves(checkerBlack).stream().anyMatch(t ->
//          t.getCoordinates().getCol() != 4 && t.getCoordinates().getRow() < 5));
//      assertTrue(myBoard.getMoves(checkerBlack).contains(myBoard.getTile(Coordinate.of(4, 4))));
//
//      myBoard = BoardSetup.createLocalBoard(GRAVITY_FILE_TWO);
//      checkerRed = myBoard.getTile(Coordinate.of(5, 8)).getPiece().get();
//      checkerBlack = myBoard.getTile(Coordinate.of(0, 8)).getPiece().get();
//      assertFalse(myBoard.getMoves(checkerRed).stream().anyMatch(t -> t.getCoordinates().getCol() > 0));
//      myBoard.move(checkerRed, Coordinate.of(4, 0));
//      assertFalse(myBoard.getMoves(checkerBlack).stream().anyMatch(t ->
//          t.getCoordinates().getRow() != 4 && t.getCoordinates().getCol() > 0));
//      assertTrue(myBoard.getMoves(checkerBlack).contains(myBoard.getTile(Coordinate.of(4, 1))));
//    } catch (Exception e) {
//      fail();
//    }
//  }
//}