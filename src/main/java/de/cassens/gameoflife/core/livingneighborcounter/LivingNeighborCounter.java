package de.cassens.gameoflife.core.livingneighborcounter;

import de.cassens.gameoflife.model.cell.Cell;

public class LivingNeighborCounter {
    public int countLivingNeighbors(Cell cell, Cell[][] cells) {
        int maxRowPos = cells.length - 1;
        int maxColPos = cells[0].length - 1;

        // get current position
        int rowPos = cell.getRow();
        int colPos = cell.getCol();

        // prepare neighbor positions
        int leftCol = colPos - 1;
        if (leftCol < 0) leftCol = maxColPos; // if the left neighbors are on the other side of the cells
        int rightCol = colPos + 1;
        if (rightCol > maxColPos) rightCol = 0; // if the right neighbors are on the other side of the cells

        int rowAbove = rowPos - 1;
        if (rowAbove < 0) rowAbove = maxRowPos; // if the neighbors above are on the other side of the cells
        int rowBelow = rowPos + 1;
        if (rowBelow > maxRowPos) rowBelow = 0; // if the neighbors below are on the other side of the cells

        // add status of neighbors to array
        int livingNeighborCount = 0;
        livingNeighborCount += cells[rowPos][leftCol].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowAbove][leftCol].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowAbove][colPos].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowAbove][rightCol].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowPos][rightCol].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowBelow][rightCol].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowBelow][colPos].isAlive() ? 1 : 0;
        livingNeighborCount += cells[rowBelow][leftCol].isAlive() ? 1 : 0;

        return livingNeighborCount;
    }
}
