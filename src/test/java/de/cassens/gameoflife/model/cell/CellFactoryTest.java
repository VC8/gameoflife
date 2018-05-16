package de.cassens.gameoflife.model.cell;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CellFactoryTest {

    @Test
    public void shouldCreateCell() {
        // given
        int row = 1;
        int col = 1;

        // when
        Cell cell = CellFactory.createCell(row, col, true);

        // then
        assertThat(cell.getRow(), is(row));
        assertThat(cell.getCol(), is(col));
        assertThat(cell.isAlive(), is(true));
    }

    @Test
    public void shouldCreateCellFromCell() {
        // given
        Cell expectedCell = new Cell(1, 1, true);

        // when
        Cell actualCell = CellFactory.createCell(expectedCell, false);

        // then
        assertThat(actualCell.getRow(), is(expectedCell.getRow()));
        assertThat(actualCell.getCol(), is(expectedCell.getCol()));
        assertThat(actualCell.isAlive(), is(!expectedCell.isAlive()));
    }
}