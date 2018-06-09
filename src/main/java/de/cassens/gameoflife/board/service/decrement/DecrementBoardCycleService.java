package de.cassens.gameoflife.board.service.decrement;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import de.cassens.gameoflife.messaging.EventEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class DecrementBoardCycleService {

    private final BoardEventRepository boardEventRepository;
    private final BoardEventFactory boardEventFactory;
    private final BoardStateService boardStateService;
    private final EventEmitter eventEmitter;

    @Autowired
    public DecrementBoardCycleService(BoardEventRepository boardEventRepository,
                                      BoardEventFactory boardEventFactory,
                                      BoardStateService boardStateService,
                                      EventEmitter eventEmitter) {
        this.boardEventRepository = boardEventRepository;
        this.boardEventFactory = boardEventFactory;
        this.boardStateService = boardStateService;
        this.eventEmitter = eventEmitter;
    }

    public void decrementBoardCycle() throws IOException {
        Board state = boardStateService.getState();
        int generation = state.getGeneration();

        Stream<BoardEvent> boardEventStream = boardEventRepository.findAllByGenerationLessThan(generation, new Sort(Sort.Direction.DESC, "timestamp"));
        BoardEvent boardEvent = boardEventStream.limit(1).findFirst().orElseThrow(() -> new IllegalStateException("cannot decrement board cycle. board is at initial state"));

        BoardEvent boardDecrementedEvent = boardEventFactory.createBoardDecrementedEvent(boardEvent);
        boardEventRepository.save(boardDecrementedEvent);

        eventEmitter.emitEvent(boardDecrementedEvent);
    }
}
