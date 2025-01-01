package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {}
