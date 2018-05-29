package de.cassens.gameoflife.board.controller.decrement;

import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gameoflife/board")
public class DecrementBoardCycleController {

    private DecrementBoardCycleService decrementBoardCycleService;

    @Autowired
    public DecrementBoardCycleController(DecrementBoardCycleService decrementBoardCycleService) {
        this.decrementBoardCycleService = decrementBoardCycleService;
    }

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/decrement")
    public ResponseEntity decrementBoardCycle() {
        decrementBoardCycleService.decrementBoardCycle();

        return new ResponseEntity(HttpStatus.OK);
    }
}
