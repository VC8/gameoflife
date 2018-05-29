package de.cassens.gameoflife.board.service.decrement;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DecrementBoardCycleServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final int GENERATION = 5;

    private final BoardEventRepository boardEventRepository = mock(BoardEventRepository.class);
    private final BoardEventFactory boardEventFactory = mock(BoardEventFactory.class);
    private final BoardStateService boardStateService = mock(BoardStateService.class);
    private final BoardEvent boardEvent = mock(BoardEvent.class);

    private DecrementBoardCycleService decrementBoardCycleService = new DecrementBoardCycleService(
            boardEventRepository, boardEventFactory, boardStateService
    );

    @SuppressWarnings("unchecked")
    @Test
    public void shouldDecrementBoardCycle() {
        // given
        givenState();
        givenBoardEventRepository();
        BoardEvent boardDecrementedEvent = givenBoardEvent();

        // when
        decrementBoardCycleService.decrementBoardCycle();

        // then
        verify(boardEventRepository).save(eq(boardDecrementedEvent));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldThrowIllegalStateExceptionWhenBoardCycleIsNeverIncremented() {
        // given
        givenState();

        when(boardEventRepository.findAllByGenerationLessThan(eq(GENERATION), eq(new Sort(Sort.Direction.DESC, "timestamp")))).thenReturn(mock(Stream.class));

        // expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("cannot decrement board cycle. board is at initial state");

        // when
        decrementBoardCycleService.decrementBoardCycle();
    }

    private BoardEvent givenBoardEvent() {
        BoardEvent boardDecrementedEvent = mock(BoardEvent.class);
        when(boardEventFactory.createBoardDecrementedEvent(eq(boardEvent))).thenReturn(boardDecrementedEvent);
        return boardDecrementedEvent;
    }

    private void givenBoardEventRepository() {
        List<BoardEvent> boardEventList = Arrays.asList(boardEvent, mock(BoardEvent.class), mock(BoardEvent.class));
        Stream<BoardEvent> stream = boardEventList.stream();
        when(boardEventRepository.findAllByGenerationLessThan(eq(GENERATION), eq(new Sort(Sort.Direction.DESC, "timestamp")))).thenReturn(stream);
    }

    private void givenState() {
        Board board = mock(Board.class);
        when(board.getGeneration()).thenReturn(GENERATION);
        when(boardStateService.getState()).thenReturn(board);
    }
}