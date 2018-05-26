package de.cassens.gameoflife.cell.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellFactory {

    public static Cell createCell(int row, int col, boolean isAlive) {
        return new Cell(row, col, isAlive);
    }

    public static Cell createCell(Cell cell, boolean isAlive) {
        return new Cell(cell.getRow(), cell.getCol(), isAlive);
    }
}
