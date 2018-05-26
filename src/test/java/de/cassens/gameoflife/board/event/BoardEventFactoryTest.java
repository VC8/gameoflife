package de.cassens.gameoflife.board.event;

import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardEventFactoryTest {

    private BoardEventFactory boardEventFactory = new BoardEventFactory();

    @Test
    public void shouldCreateBoardCreatedEvent() {
        // when
        BoardEvent createBoardEvent = boardEventFactory.createBoardCreatedEvent(3,3);

        // then
        assertThat(createBoardEvent, instanceOf(BoardEvent.class));
        assertThat(createBoardEvent.getEventName(), is("BOARD_CREATED"));
        assertThat(createBoardEvent.getTimestamp(), instanceOf(Date.class));
        assertThat(createBoardEvent.getEventId(), instanceOf(UUID.class));
        assertThat(createBoardEvent.getGeneration(), is(0));
        assertThat(createBoardEvent.getCells().length, is(3));
        assertThat(createBoardEvent.getCells()[0].length, is(3));
        assertThat(createBoardEvent.getCells()[1].length, is(3));
        assertThat(createBoardEvent.getCells()[2].length, is(3));
    }

    @Test
    public void shouldCreateBoardIncrementedEvent() {
        // given
        Cell[][] cells = TestBoardFactory.createBoard();

        // when
        BoardEvent boardIncrementedEvent = boardEventFactory.createBoardIncrementedEvent(1, cells);

        // then
        assertThat(boardIncrementedEvent, instanceOf(BoardEvent.class));
        assertThat(boardIncrementedEvent.getEventName(), is("BOARD_INCREMENTED"));
        assertThat(boardIncrementedEvent.getTimestamp(), instanceOf(Date.class));
        assertThat(boardIncrementedEvent.getEventId(), instanceOf(UUID.class));
        assertThat(boardIncrementedEvent.getGeneration(), is(1));
        assertThat(boardIncrementedEvent.getCells().length, is(3));
        assertThat(boardIncrementedEvent.getCells()[0].length, is(3));
        assertThat(boardIncrementedEvent.getCells()[1].length, is(3));
        assertThat(boardIncrementedEvent.getCells()[2].length, is(3));
    }

    @Test
    public void shouldCreateBoardDecrementedEvent() {
        // given
        BoardEvent boardEvent = givenBoardEvent();

        // when
        BoardEvent boardDecrementedEvent = boardEventFactory.createBoardDecrementedEvent(boardEvent);

        // then
        assertThat(boardDecrementedEvent, instanceOf(BoardEvent.class));
        assertThat(boardDecrementedEvent.getEventName(), is("BOARD_DECREMENTED"));
        assertThat(boardDecrementedEvent.getTimestamp(), instanceOf(Date.class));
        assertThat(boardDecrementedEvent.getEventId(), instanceOf(UUID.class));
        assertThat(boardDecrementedEvent.getGeneration(), is(3));
        assertThat(boardDecrementedEvent.getCells().length, is(3));
        assertThat(boardDecrementedEvent.getCells()[0].length, is(3));
        assertThat(boardDecrementedEvent.getCells()[1].length, is(3));
        assertThat(boardDecrementedEvent.getCells()[2].length, is(3));
    }

    private BoardEvent givenBoardEvent() {
        BoardEvent boardEvent = mock(BoardEvent.class);
        when(boardEvent.getGeneration()).thenReturn(3);
        Cell[][] cells = TestBoardFactory.createBoard();
        when(boardEvent.getCells()).thenReturn(cells);
        return boardEvent;
    }
}