package io.dflowers.remittanceservice.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionType {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW"),
    SEND("SEND"),
    RECEIVED("RECEIVED");

    private final String value;
}
