package de.cassens.gameoflife.core.cellstateupdater;

import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CellStateUpdaterTest {

    private final Cell[][] board = TestBoardFactory.createBoard();
    private final CellStateUpdater cellStateUpdater = new CellStateUpdater(board);

    @Test
    public void shouldReturnCellStatesForNextGeneration() {
        // when
        Cell[][] updatedCells = cellStateUpdater.updateCellStates();

        // then
        assertThat(updatedCells.length, is(board.length));
        assertThat(updatedCells[0].length, is(board[0].length));
        assertThat(updatedCells[0][0].isAlive(), is(true));
        assertThat(updatedCells[0][1].isAlive(), is(true));
        assertThat(updatedCells[0][2].isAlive(), is(false));
        assertThat(updatedCells[1][0].isAlive(), is(false));
        assertThat(updatedCells[1][1].isAlive(), is(false));
        assertThat(updatedCells[1][2].isAlive(), is(true));
        assertThat(updatedCells[2][0].isAlive(), is(false));
        assertThat(updatedCells[2][1].isAlive(), is(false));
        assertThat(updatedCells[2][2].isAlive(), is(true));
    }
}