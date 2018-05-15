package de.cassens.gameoflife.core.board;

import de.cassens.gameoflife.core.boardprinter.BoardPrinter;
import de.cassens.gameoflife.core.cellstateupdater.CellStateUpdater;
import de.cassens.gameoflife.model.cell.Cell;
import de.cassens.gameoflife.model.cell.CellFactory;

public class Board {

    private Cell[][] cells;
    private int generation;

    public Board(int rows, int cols) {
        this.generation = 0;

        createCells(rows, cols);
    }

    public void printBoard() {
        BoardPrinter boardPrinter = new BoardPrinter();
        boardPrinter.printBoard(this.generation, this.cells);
    }

    public void nextCycle() {
        CellStateUpdater cellStateUpdater = new CellStateUpdater(this.cells);
        this.cells = cellStateUpdater.updateCellStates();

        this.generation++;
    }

    private void createCells(int rows, int cols) {
        this.cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isAlive = Math.random() < 0.4;
                this.cells[row][col] = CellFactory.createCell(row, col, isAlive);
            }
        }
    }
}
