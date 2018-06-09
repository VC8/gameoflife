package de.cassens.gameoflife.board.controller.decrement;

import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
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
public class DecrementBoardCycleControllerTest {

    private final DecrementBoardCycleService decrementBoardCycleService = mock(DecrementBoardCycleService.class);
    private final DecrementBoardCycleController decrementBoardCycleController = new DecrementBoardCycleController(decrementBoardCycleService);


    @Test
    public void shouldDecrementBoardCycle() throws IOException {
        // when
        ResponseEntity responseEntity = decrementBoardCycleController.decrementBoardCycle();

        // then
        verify(decrementBoardCycleService).decrementBoardCycle();
        assertThat(responseEntity.getStatusCodeValue(), is(200));
    }
}