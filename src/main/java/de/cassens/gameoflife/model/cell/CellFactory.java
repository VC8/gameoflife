package de.cassens.gameoflife.model.cell;

public class CellFactory {

    private CellFactory() {
    }

    public static Cell createCell(int row, int col, boolean isAlive) {
        return new Cell(row, col, isAlive);
    }

    public static Cell createCell(Cell cell, boolean isAlive) {
        return new Cell(cell.getRow(), cell.getCol(), isAlive);
    }
}
