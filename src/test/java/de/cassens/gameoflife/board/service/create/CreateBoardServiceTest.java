package de.cassens.gameoflife.board.service.create;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.messaging.EventEmitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateBoardServiceTest {

    private final BoardEventFactory  boardEventFactory = mock(BoardEventFactory.class);
    private final BoardEventRepository boardEventRepository = mock(BoardEventRepository.class);
    private final EventEmitter eventEmitter = mock(EventEmitter.class);
    private final CreateBoardService createBoardService = new CreateBoardService(boardEventFactory, boardEventRepository, eventEmitter);

    @Test
    public void shouldCreateBoard() throws IOException {
        // given
        int rows = 3;
        int cols = 3;
        final BoardEvent boardEvent = givenCreateBoardEventFactory(rows, cols);

        // when
        createBoardService.createBoard(rows, cols);

        // then
        verify(boardEventRepository).save(boardEvent);
        verify(eventEmitter).emitEvent(boardEvent);
    }

    private BoardEvent givenCreateBoardEventFactory(int rows, int cols) {
        BoardEvent boardEvent = mock(BoardEvent.class);
        when(boardEventFactory.createBoardCreatedEvent(eq(rows), eq(cols))).thenReturn(boardEvent);
        return boardEvent;
    }
}