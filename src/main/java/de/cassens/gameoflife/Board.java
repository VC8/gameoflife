package de.cassens.gameoflife;

import de.cassens.gameoflife.core.cellstateupdater.CellStateUpdater;
import de.cassens.gameoflife.model.cell.Cell;

import java.util.Arrays;

public class Board {

    private Cell[][] cells;
    private int generation;
    private final int maxRowPos;
    private final int maxColPos;

    public Board(int rows, int cols) {
        this.generation = 0;
        this.cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isAlive = Math.random() < 0.4;
                this.cells[row][col] = new Cell(row, col, isAlive);
            }
        }

        this.maxRowPos = rows - 1;
        this.maxColPos = cols - 1;
    }

    public void printBoard() {
        // TODO printer
        System.out.println("----- print generation: " + this.generation + " -----");
        Arrays.stream(this.cells).forEach(row -> {
            Arrays.stream(row).forEach(System.out::print);
            System.out.println();
        });
    }

    public void nextGeneration() {
        CellStateUpdater cellStateUpdater = new CellStateUpdater(this.cells);
        this.cells = cellStateUpdater.updateCells();

        this.generation++;
    }
}
