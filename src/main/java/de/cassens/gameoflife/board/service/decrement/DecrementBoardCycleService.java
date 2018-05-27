package de.cassens.gameoflife.board.service.decrement;

import de.cassens.gameoflife.board.event.BoardEvent;
import de.cassens.gameoflife.board.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecrementBoardCycleService {

    private BoardEventRepository boardEventRepository;
    private BoardEventFactory boardEventFactory;

    @Autowired
    public DecrementBoardCycleService(BoardEventRepository boardEventRepository, BoardEventFactory boardEventFactory) {
        this.boardEventRepository = boardEventRepository;
        this.boardEventFactory = boardEventFactory;
    }

    public void decrementBoardCycle() {
        List<BoardEvent> boardEvents = boardEventRepository.findAll(new Sort(Sort.Direction.DESC, "timestamp"));

        BoardEvent latestEvent = boardEvents.get(0);
        if (latestEvent.getGeneration() == 0) throw new IllegalStateException("cannot decrement board cycle. board is at initial state");

        BoardEvent boardEvent = boardEvents.stream().filter(event -> latestEvent.getGeneration() > event.getGeneration()).findFirst().get();
        BoardEvent boardDecrementedEvent = boardEventFactory.createBoardDecrementedEvent(boardEvent);
        boardEventRepository.save(boardDecrementedEvent);

        // TODO emit event / send message
    }
}
