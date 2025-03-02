package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.bid.Bid;

import java.util.List;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    List<Bid> getByStatus(Bid.Status status);
    List<Bid> getBySenderLoginOrderByIdDesc(String login);
}
