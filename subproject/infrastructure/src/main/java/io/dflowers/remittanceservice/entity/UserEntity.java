package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    public static UserEntity from(User user) {
        return new UserEntity(
            user.id(),
            user.name(),
            user.created(),
            user.modified(),
            user.deleted()
        );
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private OffsetDateTime created;

    @Column(nullable = false)
    private OffsetDateTime modified;

    @Nullable
    @Column()
    private OffsetDateTime deleted;

    public User toDomain() {
        return new User(
            id,
            name,
            created,
            modified,
            deleted
        );
    }
}
