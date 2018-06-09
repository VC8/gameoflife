package de.cassens.gameoflife.board.controller.increment;

import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IncrementBoardControllerTest {

    private final IncrementBoardCycleService incrementBoardCycleService = mock(IncrementBoardCycleService.class);
    private final IncrementBoardController incrementBoardController = new IncrementBoardController(incrementBoardCycleService);

    @Test
    public void shouldIncrementBoardCycle() throws IOException {
        // when
        ResponseEntity responseEntity = incrementBoardController.incrementBoardCycle();

        // then
        verify(incrementBoardCycleService).incrementBoardCycle();
        assertThat(responseEntity.getStatusCodeValue(), is(200));
    }
}