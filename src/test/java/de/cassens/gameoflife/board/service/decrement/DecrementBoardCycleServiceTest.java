package de.cassens.gameoflife.board.service.decrement;

import de.cassens.gameoflife.board.event.BoardEvent;
import de.cassens.gameoflife.board.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DecrementBoardCycleServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private DecrementBoardCycleService decrementBoardCycleService;

    @Mock
    private BoardEventRepository boardEventRepository;
    @Mock
    private BoardEventFactory boardEventFactory;

    @Test
    public void shouldDecrementBoardCycle() {
        // given
        BoardEvent boardEvent = mock(BoardEvent.class);
        List<BoardEvent> boardEvents = Arrays.asList(mock(BoardEvent.class), boardEvent, mock(BoardEvent.class), mock(BoardEvent.class));
        when(boardEventRepository.findAll(eq(new Sort(Sort.Direction.DESC, "timestamp")))).thenReturn(boardEvents);

        BoardEvent boardDecrementedEvent = mock(BoardEvent.class);
        when(boardEventFactory.createBoardDecrementedEvent(eq(boardEvent))).thenReturn(boardDecrementedEvent);

        // when
        decrementBoardCycleService.decrementBoardCycle();

        // then
        verify(boardEventRepository).save(eq(boardDecrementedEvent));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenBoardCycleIsNeverIncremented() {
        // given
        List<BoardEvent> boardEvents = Arrays.asList(mock(BoardEvent.class));
        when(boardEventRepository.findAll(eq(new Sort(Sort.Direction.DESC, "timestamp")))).thenReturn(boardEvents);

        // expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("cannot decrement not incremented board");

        // when
        decrementBoardCycleService.decrementBoardCycle();
    }
}