package de.cassens.gameoflife.board.controller.state;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardStateControllerTest {

    private final BoardStateService boardStateService = mock(BoardStateService.class);
    private final BoardStateController boardStateController = new BoardStateController(boardStateService);

    @Test
    public void shouldGetState() {
        // given
        Cell[][] cells = TestBoardFactory.createBoard();
        int generation = 2;
        givenBoardStateService(cells, generation);

        // when
        ResponseEntity<Board> state = boardStateController.getState();

        // then
        assertThat(state.getStatusCodeValue(), is(200));
        assertThat(state.getBody().getCells(), is(cells));
        assertThat(state.getBody().getGeneration(), is(generation));
    }

    private void givenBoardStateService(Cell[][] cells, int generation) {
        Board board = mock(Board.class);
        when(board.getCells()).thenReturn(cells);
        when(board.getGeneration()).thenReturn(generation);
        when(boardStateService.getState()).thenReturn(board);
    }
}