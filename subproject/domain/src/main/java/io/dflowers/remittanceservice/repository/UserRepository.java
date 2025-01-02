package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    void delete(long id);
    Optional<User> findOne(long id);
}
