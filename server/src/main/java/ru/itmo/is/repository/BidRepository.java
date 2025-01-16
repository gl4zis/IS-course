package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.bid.Bid;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
}
