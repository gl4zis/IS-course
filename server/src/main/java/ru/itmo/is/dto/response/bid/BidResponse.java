package ru.itmo.is.dto.response.bid;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.itmo.is.dto.response.user.UserResponse;
import ru.itmo.is.entity.bid.Bid;
import ru.itmo.is.entity.bid.BidFile;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BidResponse {
    Long number;
    UserResponse sender;
    @Nullable
    UserResponse manager;
    String text;
    Bid.Type type;
    List<String> attachments;
    Bid.Status status;

    public BidResponse(Bid bid) {
        this.number = bid.getId();
        this.sender = new UserResponse(bid.getSender());
        this.manager = bid.getManager() == null ? null : new UserResponse(bid.getManager());
        this.text = bid.getText();
        this.type = bid.getType();
        this.attachments = bid.getFiles().stream().map(BidFile::getKey).toList();
        this.status = bid.getStatus();
    }
}
