package de.cassens.gameoflife.board.repository;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface BoardEventRepository extends MongoRepository<BoardEvent, UUID> {
    Stream<BoardEvent> findAllByGenerationLessThan(int generation, Sort sort);
}
