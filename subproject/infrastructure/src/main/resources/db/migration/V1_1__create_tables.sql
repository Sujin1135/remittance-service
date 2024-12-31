CREATE TABLE customers
(
    id       BIGINT AUTO_INCREMENT               NOT NULL
        PRIMARY KEY,
    name     VARCHAR(50)                         NOT NULL, -- 고객명
    created  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted  TIMESTAMP                           NULL
);

CREATE TABLE bank_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- 사용자 식별자
    name VARCHAR(50) NOT NULL, -- 계좌명
    bank ENUM('KBank', 'Shinhan', 'Hana', 'Woori', 'KakaoBank') NOT NULL, -- 은행
    account_number VARCHAR(20) NOT NULL UNIQUE, -- 계좌번호
    balance DECIMAL(18, 2) NOT NULL DEFAULT 0, -- 잔액
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES customers (id)
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL, -- 거래 계좌 ID
    related_account_id BIGINT, -- 관련된 계좌 ID (수취 계좌)
    transaction_type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER') NOT NULL, -- 거래 유형
    amount DECIMAL(18, 2) NOT NULL, -- 거래 금액
    fee DECIMAL(18, 2) NOT NULL DEFAULT 0, -- 수수료 (이체일 경우)
    balance_after DECIMAL(18, 2) NOT NULL, -- 거래 후 잔액
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 거래 시간
    INDEX (account_id, created DESC), -- 최신 거래 내역 조회 최적화
    FOREIGN KEY (account_id) REFERENCES bank_accounts(id),
    FOREIGN KEY (related_account_id) REFERENCES bank_accounts(id)
);

CREATE TABLE daily_limits (
    account_id BIGINT NOT NULL, -- 계좌 ID
    date DATE NOT NULL, -- 해당 날짜
    daily_withdraw_limit DECIMAL(18, 2) NOT NULL DEFAULT 1000000, -- 출금 일 한도
    daily_transfer_limit DECIMAL(18, 2) NOT NULL DEFAULT 3000000, -- 이체 일 한도
    total_withdraw DECIMAL(18, 2) NOT NULL DEFAULT 0, -- 오늘 출금 합계
    total_transfer DECIMAL(18, 2) NOT NULL DEFAULT 0, -- 오늘 이체 합계
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (account_id, date),
    FOREIGN KEY (account_id) REFERENCES bank_accounts(id)
);
