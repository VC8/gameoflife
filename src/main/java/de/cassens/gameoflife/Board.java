package de.cassens.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private Cell[][] cells;
    private int generation;

    public Board(int rows, int cols) {
        this.generation = 0;
        this.cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isAlive = Math.random() > 0.4;
                this.cells[row][col] = new Cell(row, col, isAlive);
            }
        }
    }

    public void printBoard() {
        System.out.println("----- print generation: " + this.generation + " -----");
        Arrays.stream(this.cells).forEach(row -> {
            Arrays.stream(row).forEach(System.out::print);
            System.out.println();
        });
    }

    public void nextGeneration() {
        int maxRows = this.cells.length - 1;
        Arrays.stream(this.cells).forEach(row -> {
            int maxCols = row.length - 1;

            Arrays.stream(row).forEach(cell -> {
                // get current position
                int rowPos = cell.getRow();
                int colPos = cell.getCol();

                // prepare neighbors
                int leftCol = colPos - 1;
                if (leftCol < 0) leftCol = maxCols; // if the left neighbors are on the other side of the board
                int rightCol = colPos + 1;
                if (rightCol > maxCols) rightCol = 0; // if the right neighbors are on the other side of the board
                int rowAbove = rowPos - 1;
                if (rowAbove < 0) rowAbove = maxRows; // if the neighbors above are on the other side of the board
                int rowBelow = rowPos + 1;
                if (rowBelow > maxRows) rowBelow = 0; // if the neighbors below are on the other side of the board

                // add status of neighbors to array
                List<Boolean> neighbors = new ArrayList<>();
                neighbors.add(this.cells[rowPos][leftCol].isAlive());
                neighbors.add(this.cells[rowAbove][leftCol].isAlive());
                neighbors.add(this.cells[rowAbove][colPos].isAlive());
                neighbors.add(this.cells[rowAbove][rightCol].isAlive());
                neighbors.add(this.cells[rowPos][rightCol].isAlive());
                neighbors.add(this.cells[rowBelow][rightCol].isAlive());
                neighbors.add(this.cells[rowBelow][colPos].isAlive());
                neighbors.add(this.cells[rowBelow][leftCol].isAlive());

                long livingNeighborCount = neighbors.stream().filter(Boolean::booleanValue).count();

                // 1. living cell with less than 2 living neighbors dies
                if (cell.isAlive() && livingNeighborCount < 2) cell.setAlive(false);

                // 2. living cell with 2 or more living neighbors stays alive
                if (cell.isAlive() && livingNeighborCount == 2 || cell.isAlive() && livingNeighborCount == 3) cell.setAlive(true); // can be deleted

                // 3. living cell with more than 3 living neighbors dies
                if (cell.isAlive() && livingNeighborCount > 3) cell.setAlive(false);

                // 4. dead cell with 3 living neighbors comes to life
                if (!cell.isAlive() && livingNeighborCount == 3) cell.setAlive(true);
            });
        });

        this.generation++;
    }
}
