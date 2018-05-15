package de.cassens.gameoflife.util.converter;

import de.cassens.gameoflife.model.cell.Cell;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CellListToCellArrayConverterTest {

    private final CellListToCellArrayConverter cellListToCellArrayConverter = new CellListToCellArrayConverter();

    @Test
    public void shouldConvertCellListToCellArray() {
        // given
        Cell expectedCell = new Cell(0, 0, true);
        Cell expectedCell2 = new Cell(0, 1, false);
        Cell expectedCell3 = new Cell(0, 2, true);
        List<Cell> cellList = givenCellList(expectedCell, expectedCell2, expectedCell3);

        // when
        Cell[] cells = cellListToCellArrayConverter.convertCellListToCellArray(cellList);

        // then
        assertThat(cells.length, is(cellList.size()));

        Cell actualCell = cells[0];
        assertThat(actualCell.getRow(), is(expectedCell.getRow()));
        assertThat(actualCell.getCol(), is(expectedCell.getCol()));
        assertThat(actualCell.isAlive(), is(expectedCell.isAlive()));
    }

    private List<Cell> givenCellList(Cell... cells) {
        return new ArrayList<>(Arrays.asList(cells));
    }
}