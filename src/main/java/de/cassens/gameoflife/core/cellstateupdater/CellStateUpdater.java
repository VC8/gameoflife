package de.cassens.gameoflife.core.cellstateupdater;

import de.cassens.gameoflife.core.livingneighborcounter.LivingNeighborCounter;
import de.cassens.gameoflife.model.cell.Cell;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CellStateUpdater {

    private final Cell[][] cells;

    public CellStateUpdater(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getUpdatedCells() {
        Cell[][] updatedCells = {};
        return Arrays.stream(this.cells).map(this::updateCellStates).collect(Collectors.toList()).toArray(updatedCells);
    }

    private Cell[] updateCellStates(Cell[] row) {
        Cell[] cellArray = {};
        return Arrays.stream(row).map(this::updateCellState).collect(Collectors.toList()).toArray(cellArray);
    }

    private Cell updateCellState(Cell cell) {
        LivingNeighborCounter livingNeighborCounter = new LivingNeighborCounter();
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, this.cells);

        return getCellWithNewState(cell, livingNeighborCount);
    }

    private Cell getCellWithNewState(Cell cell, int livingNeighborCount) {
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
