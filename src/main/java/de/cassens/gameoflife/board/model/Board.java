package de.cassens.gameoflife.board.model;

import de.cassens.gameoflife.cell.model.Cell;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class Board {

    Cell[][] cells;
    int generation;

//    public void incrementCycle() {
//        CellStateUpdater cellStateUpdater = new CellStateUpdater(this.cells);
//        this.cells = cellStateUpdater.updateCellStates();
//
//        this.generation++;
//    }
}
