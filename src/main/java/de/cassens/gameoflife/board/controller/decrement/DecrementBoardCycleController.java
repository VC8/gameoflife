package de.cassens.gameoflife.board.controller.decrement;

import de.cassens.gameoflife.board.controller.ApiMapping;
import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@ApiMapping
public class DecrementBoardCycleController {

    private DecrementBoardCycleService decrementBoardCycleService;

    @Autowired
    public DecrementBoardCycleController(DecrementBoardCycleService decrementBoardCycleService) {
        this.decrementBoardCycleService = decrementBoardCycleService;
    }

    @PostMapping("/decrement")
    public ResponseEntity decrementBoardCycle() throws IOException {
        decrementBoardCycleService.decrementBoardCycle();

        return new ResponseEntity(HttpStatus.OK);
    }
}
