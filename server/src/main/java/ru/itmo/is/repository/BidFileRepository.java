package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.bid.BidFile;

@Repository
public interface BidFileRepository extends CrudRepository<BidFile, String> {
}
