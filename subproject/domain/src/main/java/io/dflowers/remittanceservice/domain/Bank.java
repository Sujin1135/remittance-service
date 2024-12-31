package io.dflowers.remittanceservice.domain;

public enum Bank {
    KBANK("KBank"),
    SHINHAN("Shinhan"),
    HANA("Hana"),
    WOORI("Woori"),
    KAKAO("KakaoBank");

    private final String value;

    Bank(String value) {
        this.value = value;
    }
}
