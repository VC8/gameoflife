package de.cassens.gameoflife.board.controller.decrement;

import de.cassens.gameoflife.board.controller.ApiMapping;
import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiMapping
public class DecrementBoardCycleController {

    private DecrementBoardCycleService decrementBoardCycleService;

    @Autowired
    public DecrementBoardCycleController(DecrementBoardCycleService decrementBoardCycleService) {
        this.decrementBoardCycleService = decrementBoardCycleService;
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    @PostMapping("/decrement")
    public ResponseEntity decrementBoardCycle() {
        decrementBoardCycleService.decrementBoardCycle();

        return new ResponseEntity(HttpStatus.OK);
    }
}
