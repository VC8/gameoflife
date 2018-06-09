package de.cassens.gameoflife.board.service.decrement;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import de.cassens.gameoflife.messaging.EventEmitter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class DecrementBoardCycleServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final int GENERATION = 5;

    private final BoardEventRepository boardEventRepository = mock(BoardEventRepository.class);
    private final BoardEventFactory boardEventFactory = mock(BoardEventFactory.class);
    private final BoardStateService boardStateService = mock(BoardStateService.class);
    private final BoardEvent boardEvent = mock(BoardEvent.class);
    private final EventEmitter eventEmitter = mock(EventEmitter.class);

    private DecrementBoardCycleService decrementBoardCycleService = new DecrementBoardCycleService(
            boardEventRepository, boardEventFactory, boardStateService,
            eventEmitter);

    @Test
    public void shouldDecrementBoardCycle() throws IOException {
        // given
        givenState();
        givenBoardEventRepository();
        BoardEvent boardDecrementedEvent = givenBoardEvent();

        // when
        decrementBoardCycleService.decrementBoardCycle();

        // then
        verify(boardEventRepository).save(boardDecrementedEvent);
        verify(eventEmitter).emitEvent(boardDecrementedEvent);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldThrowIllegalStateExceptionWhenBoardCycleIsNeverIncremented() throws IOException {
        // given
        givenState();

        when(boardEventRepository.findAllByGenerationLessThan(GENERATION, new Sort(Sort.Direction.DESC, "timestamp"))).thenReturn(mock(Stream.class));

        // expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("cannot decrement board cycle. board is at initial state");

        // when
        decrementBoardCycleService.decrementBoardCycle();
    }

    private BoardEvent givenBoardEvent() {
        BoardEvent boardDecrementedEvent = mock(BoardEvent.class);
        when(boardEventFactory.createBoardDecrementedEvent(boardEvent)).thenReturn(boardDecrementedEvent);
        return boardDecrementedEvent;
    }

    private void givenBoardEventRepository() {
        Stream<BoardEvent> stream = Stream.of(boardEvent, mock(BoardEvent.class), mock(BoardEvent.class));
        when(boardEventRepository.findAllByGenerationLessThan(GENERATION, new Sort(Sort.Direction.DESC, "timestamp"))).thenReturn(stream);
    }

    private void givenState() {
        Board board = mock(Board.class);
        when(board.getGeneration()).thenReturn(GENERATION);
        when(boardStateService.getState()).thenReturn(board);
    }
}