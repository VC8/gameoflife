package de.cassens.gameoflife;

import java.util.Arrays;

public class Board {

    private Cell[][] cells;
    private int generation;
    private int maxRowPos;
    private int maxColPos;

    public Board(int rows, int cols) {
        this.generation = 0;
        this.cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isAlive = Math.random() > 0.4;
                this.cells[row][col] = new Cell(row, col, isAlive);
            }
        }

        this.maxRowPos = rows - 1;
        this.maxColPos = cols - 1;
    }

    public void printBoard() {
        System.out.println("----- print generation: " + this.generation + " -----");
        Arrays.stream(this.cells).forEach(row -> {
            Arrays.stream(row).forEach(System.out::print);
            System.out.println();
        });
    }

    public void nextGeneration() {
        Arrays.stream(this.cells).forEach(row -> Arrays.stream(row).forEach(cell -> {
            // get current position
            int rowPos = cell.getRow();
            int colPos = cell.getCol();

            // prepare neighbor positions
            int leftCol = colPos - 1;
            if (leftCol < 0) leftCol = this.maxColPos; // if the left neighbors are on the other side of the board
            int rightCol = colPos + 1;
            if (rightCol > this.maxColPos) rightCol = 0; // if the right neighbors are on the other side of the board

            int rowAbove = rowPos - 1;
            if (rowAbove < 0) rowAbove = this.maxRowPos; // if the neighbors above are on the other side of the board
            int rowBelow = rowPos + 1;
            if (rowBelow > this.maxRowPos) rowBelow = 0; // if the neighbors below are on the other side of the board

            // add status of neighbors to array
            int livingNeighborCount = 0;
            livingNeighborCount += this.cells[rowPos][leftCol].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowAbove][leftCol].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowAbove][colPos].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowAbove][rowAbove].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowPos][rightCol].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowBelow][rightCol].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowBelow][colPos].isAlive() ? 1 : 0;
            livingNeighborCount += this.cells[rowBelow][leftCol].isAlive() ? 1 : 0;

            // 1. living cell with less than 2 living neighbors dies
            if (cell.isAlive() && livingNeighborCount < 2) cell.setAlive(false);

            // 3. living cell with more than 3 living neighbors dies
            if (cell.isAlive() && livingNeighborCount > 3) cell.setAlive(false);

            // 4. dead cell with 3 living neighbors comes to life
            if (!cell.isAlive() && livingNeighborCount == 3) cell.setAlive(true);
        }));

        this.generation++;
    }
}
