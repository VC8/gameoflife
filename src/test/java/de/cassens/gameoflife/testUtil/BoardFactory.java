package de.cassens.gameoflife.testUtil;

import de.cassens.gameoflife.model.cell.Cell;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Cell[][] createBoard() {
        return new Cell[][]{
                new Cell[]{new Cell(0,0, true), new Cell(0,1, true), new Cell(0,2, false)},
                new Cell[]{new Cell(1,0, true), new Cell(1,1, false), new Cell(1,2, true)},
                new Cell[]{new Cell(2,0, false), new Cell(2,1, false), new Cell(2,2, true)}
        };
    }
}
