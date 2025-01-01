package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.User;

public interface UserRepository {
    User save(User user);
}
