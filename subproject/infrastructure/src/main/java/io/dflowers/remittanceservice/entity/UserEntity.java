package io.dflowers.remittanceservice.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "user")
    private Set<BankAccountEntity> bankAccounts;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private OffsetDateTime created;

    @Column(nullable = false)
    private OffsetDateTime modified;

    @Nullable
    @Column(nullable = true)
    private OffsetDateTime deleted;
}
