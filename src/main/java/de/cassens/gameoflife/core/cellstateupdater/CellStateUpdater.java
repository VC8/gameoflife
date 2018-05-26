package de.cassens.gameoflife.core.cellstateupdater;

import de.cassens.gameoflife.core.livingneighborcounter.LivingNeighborCounter;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.cell.model.CellFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CellStateUpdater {

    private final Cell[][] cells;

    public CellStateUpdater(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] updateCellStates() {
        Cell[][] updatedCells = {};
        return Arrays.stream(this.cells).map(this::updateRowCellStates).collect(Collectors.toList()).toArray(updatedCells);
    }

    private Cell[] updateRowCellStates(Cell[] row) {
        Cell[] cellArray = {};
        return Arrays.stream(row).map(this::updateCellState).collect(Collectors.toList()).toArray(cellArray);
    }

    private Cell updateCellState(Cell cell) {
        LivingNeighborCounter livingNeighborCounter = new LivingNeighborCounter();
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, this.cells);

        return getCellWithNewState(cell, livingNeighborCount);
    }

    private Cell getCellWithNewState(Cell cell, int livingNeighborCount) {

        if (cell.isAlive()) {
            // #1 rule: living cell with less than 2 living neighbors dies
            if (livingNeighborCount < 2) return CellFactory.createCell(cell, false);

            // #2 rule: living cell with 2 or 3 living neighbors stays alive
            if (livingNeighborCount < 4) return cell;

            // #3 rule: living cell with more than 3 living neighbors dies
            return CellFactory.createCell(cell, false);
        }

        // #4 rule: dead cell with 3 living neighbors comes to life
        if (livingNeighborCount == 3) return CellFactory.createCell(cell, true);

        return cell;
    }
}
