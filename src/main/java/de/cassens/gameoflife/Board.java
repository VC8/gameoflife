package de.cassens.gameoflife;

import de.cassens.gameoflife.model.cell.Cell;
import de.cassens.gameoflife.util.converter.CellListToCellArrayConverter;
import de.cassens.gameoflife.core.livingneighborcounter.LivingNeighborCounter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("----- print generation: " + this.generation + " -----");
        Arrays.stream(this.cells).forEach(row -> {
            Arrays.stream(row).forEach(System.out::print);
            System.out.println();
        });
    }

    public void nextGeneration() {
        List<Cell[]> updatedCells = Arrays.stream(this.cells).map(this::updateCellStates).collect(Collectors.toList());

        updateBoardCells(updatedCells);

        this.generation++;
    }

    private void updateBoardCells(List<Cell[]> updatedCells) {
        updatedCells.toArray(this.cells);
    }

    private Cell[] updateCellStates(Cell[] row) {
        List<Cell> rowCellsList = Arrays.stream(row).map(this::updateCellState).collect(Collectors.toList());

        CellListToCellArrayConverter cellListToCellArrayConverter = new CellListToCellArrayConverter();
        return cellListToCellArrayConverter.convertCellListToCellArray(rowCellsList);
    }

    private Cell updateCellState(Cell cell) {
        LivingNeighborCounter livingNeighborCounter = new LivingNeighborCounter();
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, this.cells);

        // 1. living cell with less than 2 living neighbors dies
        if (cell.isAlive() && livingNeighborCount < 2) return new Cell(cell.getRow(), cell.getCol(), false);

        // 2. cell with 2 or 3 living neighbors stays alive
        if (cell.isAlive() && livingNeighborCount == 2 || cell.isAlive() && livingNeighborCount == 3)return new Cell(cell.getRow(), cell.getCol(), true);

        // 3. living cell with more than 3 living neighbors dies
        if (cell.isAlive() && livingNeighborCount > 3) return new Cell(cell.getRow(), cell.getCol(), false);

        // 4. dead cell with 3 living neighbors comes to life
        if (!cell.isAlive() && livingNeighborCount == 3) return new Cell(cell.getRow(), cell.getCol(), true);

        return cell;
    }
}
