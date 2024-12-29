create table bank_accounts
(
    id       varchar(36)                         not null
        primary key,
    user_id  varchar(36)                         not null,
    name     varchar(50)                         not null,
    balance  decimal   default 0                 not null,
    created  timestamp default CURRENT_TIMESTAMP not null,
    modified timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted  timestamp                           null
);

create index bank_accounts_user_id_deleted_created_index
    on bank_accounts (user_id asc, deleted asc, created desc);
