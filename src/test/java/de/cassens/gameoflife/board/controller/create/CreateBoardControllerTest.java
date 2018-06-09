package de.cassens.gameoflife.board.controller.create;

import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.board.service.create.CreateBoardService;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateBoardControllerTest {

    private static final int ROWS = 3;
    private static final int COLS = 3;

    private final CreateBoardService createBoardService = mock(CreateBoardService.class);
    private final CreateBoardController createBoardController = new CreateBoardController(createBoardService);


    @Test
    public void shouldCreateBoard() throws IOException {
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