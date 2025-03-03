package ru.itmo.is.dto.response;

public record EvictionResponse(String resident, Reason reason) {

    public enum Reason {
        NON_PAYMENT,
        NON_RESIDENCE,
        RULE_VIOLATION,
        VOLUNTARILY,
        EXPULSION
    }
}
