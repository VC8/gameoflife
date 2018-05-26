package de.cassens.gameoflife.board.controller;

import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class DecrementBoardCycleControllerTest {

    @InjectMocks
    private DecrementBoardCycleController decrementBoardCycleController;

    @Mock
    private DecrementBoardCycleService decrementBoardCycleService;

    @Test
    public void shouldDecrementBoardCycle() {
        // when
        ResponseEntity responseEntity = decrementBoardCycleController.decrementBoardCycle();

        // then
        verify(decrementBoardCycleService).decrementBoardCycle();
        assertThat(responseEntity.getStatusCodeValue(), is(200));
    }
}