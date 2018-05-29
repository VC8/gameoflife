package de.cassens.gameoflife.board.model;

import de.cassens.gameoflife.cell.model.Cell;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class Board {

    Cell[][] cells;
    int generation;

}
