package de.cassens.gameoflife.board.controller.create;

import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.board.service.create.CreateBoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateBoardControllerTest {

    private static final int ROWS = 3;
    private static final int COLS = 3;

    @InjectMocks
    private CreateBoardController createBoardController;

    @Mock
    private CreateBoardService createBoardService;

    @Test
    public void shouldCreateBoard() {
        // given
        BoardPayload boardPayload = givenBoardPayload();

        // when
        ResponseEntity response = createBoardController.createBoard(boardPayload);

        // then
        verify(createBoardService).createBoard(eq(ROWS), eq(COLS));
        assertThat(response.getStatusCodeValue(), is(201));
    }

    private BoardPayload givenBoardPayload() {
        BoardPayload boardPayload = new BoardPayload();
        boardPayload.setRows(ROWS);
        boardPayload.setCols(COLS);
        return boardPayload;
    }
}