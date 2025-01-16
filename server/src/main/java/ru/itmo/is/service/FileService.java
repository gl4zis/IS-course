package ru.itmo.is.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.is.dto.response.FileResponse;
import ru.itmo.is.entity.bid.Bid;
import ru.itmo.is.entity.bid.BidFile;
import ru.itmo.is.exception.NotFoundException;
import ru.itmo.is.repository.BidFileRepository;
import ru.itmo.is.storage.FileRecord;
import ru.itmo.is.storage.FileStorage;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final BidFileRepository bidFileRepository;
    private final FileStorage fileStorage;

    @Transactional
    public void upload(MultipartFile file) {
        FileRecord record = fileStorage.save(file);
        var bidFile = new BidFile();
        bidFile.setKey(record.key());
        bidFile.setName(record.name());
        bidFileRepository.save(bidFile);
    }

    public FileResponse get(String key) {
        Optional<BidFile> bidFileO = bidFileRepository.findById(key);
        if (bidFileO.isEmpty()) {
            throw new NotFoundException("No file with such key");
        }

        return fileStorage.get(bidFileO.get().record());
    }

    public void linkBid(String key, Bid bid) throws IOException {
        Optional<BidFile> bidFileO = bidFileRepository.findById(key);
        if (bidFileO.isEmpty()) {
            throw new NotFoundException("No file with such key");
        }
        BidFile file = bidFileO.get();
        if (file.getBid() != null) {
            throw new IOException("Bid already linked to this file");
        }
        file.setBid(bid);
        bidFileRepository.save(file);
    }
}
