package de.cassens.gameoflife.testUtil;

import de.cassens.gameoflife.model.cell.Cell;

import static de.cassens.gameoflife.model.cell.CellFactory.createCell;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Cell[][] createBoard() {
        return new Cell[][]{
                new Cell[]{createCell(0, 0, true), createCell(0, 1, true), createCell(0, 2, false)},
                new Cell[]{createCell(1, 0, false), createCell(1, 1, false), createCell(1, 2, true)},
                new Cell[]{createCell(2, 0, false), createCell(2, 1, false), createCell(2, 2, true)}
        };
    }
}
