package ru.itmo.is.dto.response.bid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.is.entity.bid.Bid;
import ru.itmo.is.entity.bid.BidFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BidResponse {

    public BidResponse(Bid bid) {
        this.number = bid.getId();
        this.sender = bid.getSender().getLogin();
        this.text = bid.getText();
        this.type = bid.getType();
        this.attachments = bid.getFiles().stream().map(BidFile::getKey).toList();
    }

    private Long number;
    private String sender;
    private String text;
    private Bid.Type type;
    private List<String> attachments;
}
