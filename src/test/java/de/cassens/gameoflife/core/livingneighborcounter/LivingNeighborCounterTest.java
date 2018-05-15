package de.cassens.gameoflife.core.livingneighborcounter;

import de.cassens.gameoflife.model.cell.Cell;
import de.cassens.gameoflife.testUtil.BoardFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LivingNeighborCounterTest {

    private final LivingNeighborCounter livingNeighborCounter = new LivingNeighborCounter();
    private final Cell[][] board = BoardFactory.createBoard();

    @Test
    public void shouldCountLivingNeighbors() {
        // given
        Cell cell = board[1][1];

        // when
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, board);

        // then
        assertThat(livingNeighborCount, is(4));
    }

    @Test
    public void shouldCountNeighborsWhenCellIsAtTheEdgeOfBoard() {
        // given
        Cell cell = board[1][2];

        // when
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, board);

        // then
        assertThat(livingNeighborCount, is(3));
    }

    @Test
    public void shouldCountNeighborsWhenCellIsAtTheTopOfBoard() {
        // given
        Cell cell = board[0][1];

        // when
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, board);

        // then
        assertThat(livingNeighborCount, is(3));
    }
}