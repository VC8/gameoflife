package de.cassens.gameoflife.util.converter;

import de.cassens.gameoflife.model.cell.Cell;

import java.util.List;

public class CellListToCellArrayConverter {
    public Cell[] convertCellListToCellArray(List<Cell> cellList) {
        Cell[] rowCellsArray = new Cell[cellList.size()];
        cellList.toArray(rowCellsArray);
        return rowCellsArray;
    }
}
