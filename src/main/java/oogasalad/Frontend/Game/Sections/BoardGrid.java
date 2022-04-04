package oogasalad.Frontend.Game.Sections;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class is handlles the chess board GridPane.
 */

public class BoardGrid {

    private GridPane myBoard;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;

    public BoardGrid(int rows, int cols) {
        myBoard = setUpBoard(rows, cols);
    }

    private GridPane setUpBoard(int rows, int cols) {
        GridPane gp = new GridPane();
        for (int i=0; i < rows; i++) {
            gp.getRowConstraints().add(new RowConstraints(WIDTH_Board / rows));
        }
        for (int k=0; k < cols; k ++) {
            gp.getColumnConstraints().add(new ColumnConstraints(HEIGHT_BOARD / cols));
        }
        for (int i=0; i < rows; i++) {
            for (int k=0; k < cols; k ++) {
                Rectangle rect = new Rectangle(WIDTH_Board / rows, HEIGHT_BOARD / cols);
                if ((i + k) % 2 == 0) {
                    rect.setFill(Color.BLACK);
                } else {
                    rect.setFill(Color.RED);
                }
                gp.add(rect, k, i);
            }
        }
        return gp;
    }

    /**
     * method for GameView to use to retrieve GridPane of Board
     * @return GridPane myBoard
     */
    public GridPane getBoard() {return myBoard;}
}
