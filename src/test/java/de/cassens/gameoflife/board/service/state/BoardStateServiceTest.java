package de.cassens.gameoflife.board.service.state;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BoardStateServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BoardStateService boardStateService = new BoardStateService();

    @Test
    public void shouldReturnStateAfterSavingEvent() {
        // given
        int generation = 5;
        Cell[][] cells = TestBoardFactory.createBoard();

        BoardEventFactory boardEventFactory = new BoardEventFactory();
        BoardEvent boardIncrementedEvent = boardEventFactory.createBoardIncrementedEvent(generation, cells);
        AfterSaveEvent<BoardEvent> afterSaveEvent = new AfterSaveEvent<>(boardIncrementedEvent, null, null);
        boardStateService.onAfterSave(afterSaveEvent);

        // when
        Board state = boardStateService.getState();

        // then
        assertThat(state.getGeneration(), is(generation));
        assertThat(state.getCells(), is(cells));
    }

    @Test
    public void shouldThrowExceptionWhenStateIsNull() {
        // expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("no board is created");

        // when
        boardStateService.getState();
    }
}