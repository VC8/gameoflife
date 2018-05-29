package de.cassens.gameoflife.board.service.create;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateBoardServiceTest {

    @InjectMocks
    private CreateBoardService createBoardService;

    @Mock
    private BoardEventFactory  boardEventFactory;
    @Mock
    private BoardEventRepository boardEventRepository;

    @Test
    public void shouldCreateBoard() {
        // given
        int rows = 3;
        int cols = 3;
        givenCreateBoardEventFactory(rows, cols);

        // when
        createBoardService.createBoard(rows, cols);

        // then
        verify(boardEventRepository).save(any(BoardEvent.class));
    }

    private void givenCreateBoardEventFactory(int rows, int cols) {
        BoardEvent boardEvent = mock(BoardEvent.class);
        when(boardEventFactory.createBoardCreatedEvent(eq(rows), eq(cols))).thenReturn(boardEvent);
    }
}