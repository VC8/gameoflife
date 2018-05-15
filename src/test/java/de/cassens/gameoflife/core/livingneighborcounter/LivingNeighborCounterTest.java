package de.cassens.gameoflife.core.livingneighborcounter;

import de.cassens.gameoflife.model.cell.Cell;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LivingNeighborCounterTest {

    private final LivingNeighborCounter livingNeighborCounter = new LivingNeighborCounter();
    private final Cell[][] board = givenBoard();

    @Test
    public void shouldCountLivingNeighbors() {
        // given
        Cell cell = board[1][1];

        // when
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, board);

        // then
        assertThat(livingNeighborCount, is(5));
    }

    @Test
    public void shouldCountNeighborsWhenCellIsAtTheEdgeOfBoard() {
        // given
        Cell cell = board[1][2];

        // when
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, board);

        // then
        assertThat(livingNeighborCount, is(4));
    }

    @Test
    public void shouldCountNeighborsWhenCellIsAtTheTopOfBoard() {
        // given
        Cell cell = board[0][1];

        // when
        int livingNeighborCount = livingNeighborCounter.countLivingNeighbors(cell, board);

        // then
        assertThat(livingNeighborCount, is(4));
    }

    private Cell[][] givenBoard() {
        return new Cell[][]{
                new Cell[]{new Cell(0,0, true), new Cell(0,1, true), new Cell(0,2, false)},
                new Cell[]{new Cell(1,0, true), new Cell(1,1, false), new Cell(1,2, true)},
                new Cell[]{new Cell(2,0, false), new Cell(2,1, false), new Cell(2,2, true)}
        };
    }
}