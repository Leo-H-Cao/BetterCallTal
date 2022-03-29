package oogasalad.GamePlayer.Board;

import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.EngineExceptions.WrongPlayerException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class ChessBoard {

  private ChessTile[][] board;
  private TurnCriteria turnCriteria;
  private Player[] players;
  private List<EndCondition> endConditions;
  private int currentPlayer;

  public ChessBoard(int length, int height, TurnCriteria turnCriteria, Player[] players, List<EndCondition> endConditions) {
    board = new ChessTile[length][height];
    this.turnCriteria = turnCriteria;
    this.players = players;
    this.endConditions = endConditions;
    currentPlayer = turnCriteria.getCurrentPlayer();
  }

  /***
   * Moves the piece to the finalSquare
   *
   * @param piece to move
   * @param finalSquare end square
   * @return set of updated tiles + next player turn
   */
  public TurnUpdate move(Piece piece, ChessTile finalSquare) throws InvalidMoveException, OutsideOfBoardException, WrongPlayerException {
    if(piece.checkTeam(turnCriteria.getCurrentPlayer())) {
      return new TurnUpdate(piece.move(finalSquare), turnCriteria.incrementTurn());
    }
    throw new WrongPlayerException(turnCriteria.getCurrentPlayer() + "");
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece) {
    return piece.getMoves();
  }

  /***
   * @return if the game is over
   */
  public boolean isGameOver() {
    return false;
  }

  /***
   * @return scores of all teams after game is over. If game isn't over, an empty map is returned.
   */
  public Map<Integer, Double> getScores() {
    return null;
  }

  /***
   * @param coordinates to check
   * @return if a given coordinate is in bounds
   */
  public boolean inBounds(Coordinate coordinates) {return coordinates.getRow() < board.length && coordinates.getCol() < board[coordinates.getRow()].length;}

  /***
   * Gets the tile at the specified coordinates
   *
   * @param coordinate is the coordinate of the tile to get
   * @return tile at specificed coordinate
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public ChessTile getTile(Coordinate coordinate) throws OutsideOfBoardException {
    if(!inBounds(coordinate)) throw new OutsideOfBoardException(coordinate.toString());
    return board[coordinate.getRow()][coordinate.getCol()];
  }

  /***
   * Returns if a tile is empty
   *
   * @param coordinate to check
   * @return if the tile is empty
   * @throws OutsideOfBoardException if the coordinate falls outside the board
   */
  public boolean isTileEmpty(Coordinate coordinate) throws OutsideOfBoardException {
    if(!inBounds(coordinate)) throw new OutsideOfBoardException(coordinate.toString());
    return getTile(coordinate).getPiece().isEmpty();
  }

  /***
   * @param id of player
   * @return player with given id
   */
  public Player getPlayer(int id) {
    return players[Math.min(id, players.length - 1)];
  }
}
