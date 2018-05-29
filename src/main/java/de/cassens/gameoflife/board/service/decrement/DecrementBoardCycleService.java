package de.cassens.gameoflife.board.service.decrement;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class DecrementBoardCycleService {

    private final BoardEventRepository boardEventRepository;
    private final BoardEventFactory boardEventFactory;
    private final BoardStateService boardStateService;

    @Autowired
    public DecrementBoardCycleService(BoardEventRepository boardEventRepository, BoardEventFactory boardEventFactory, BoardStateService boardStateService) {
        this.boardEventRepository = boardEventRepository;
        this.boardEventFactory = boardEventFactory;
        this.boardStateService = boardStateService;
    }

    public void decrementBoardCycle() {
        Board state = boardStateService.getState();
        int generation = state.getGeneration();

        Stream<BoardEvent> boardEventStream = boardEventRepository.findAllByGenerationLessThan(generation, new Sort(Sort.Direction.DESC, "timestamp"));
        BoardEvent boardEvent = boardEventStream.limit(1).findFirst().orElseThrow(() -> new IllegalStateException("cannot decrement board cycle. board is at initial state"));

        BoardEvent boardDecrementedEvent = boardEventFactory.createBoardDecrementedEvent(boardEvent);
        boardEventRepository.save(boardDecrementedEvent);
    }
}
