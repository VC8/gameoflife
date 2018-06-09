package de.cassens.gameoflife.board.service.increment;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.EventEmitter;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IncrementBoardCycleServiceTest {

    @InjectMocks
    private IncrementBoardCycleService incrementBoardCycleService;

    @Mock
    private BoardStateService boardStateService;
    @Mock
    private BoardEventRepository boardEventRepository;
    @Mock
    private BoardEventFactory boardEventFactory;
    @Mock
    private EventEmitter eventEmitter;

    @Test
    public void shouldIncrementBoardCycle() throws IOException {
        // given
        int generation = 2;
        givenBoardStateService(generation);
        BoardEvent boardIncrementedEvent = givenBoardIncrementedEvent(generation);

        // when
        incrementBoardCycleService.incrementBoardCycle();

        // then
        verify(boardEventRepository).save(boardIncrementedEvent);
        verify(eventEmitter).emitEvent(boardIncrementedEvent);
    }

    private void givenBoardStateService(int generation) {
        Board board = mock(Board.class);

        Cell[][] cells = TestBoardFactory.createBoard();
        when(board.getCells()).thenReturn(cells);
        when(board.getGeneration()).thenReturn(generation);

        when(boardStateService.getState()).thenReturn(board);
    }

    private BoardEvent givenBoardIncrementedEvent(int generation) {
        BoardEvent boardIncrementedEvent = mock(BoardEvent.class);
        when(boardEventFactory.createBoardIncrementedEvent(eq(++generation), any(Cell[][].class))).thenReturn(boardIncrementedEvent);
        return boardIncrementedEvent;
    }
}