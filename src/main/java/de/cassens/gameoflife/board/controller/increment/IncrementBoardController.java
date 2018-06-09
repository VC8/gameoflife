package de.cassens.gameoflife.board.controller.increment;

import de.cassens.gameoflife.board.controller.ApiMapping;
import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@ApiMapping
public class IncrementBoardController {

    private final IncrementBoardCycleService incrementBoardCycleService;

    @Autowired
    public IncrementBoardController(IncrementBoardCycleService incrementBoardCycleService) {
        this.incrementBoardCycleService = incrementBoardCycleService;
    }

    @PostMapping("/increment")
    public ResponseEntity incrementBoardCycle() throws IOException {
        incrementBoardCycleService.incrementBoardCycle();

        return new ResponseEntity(HttpStatus.OK);
    }
}
