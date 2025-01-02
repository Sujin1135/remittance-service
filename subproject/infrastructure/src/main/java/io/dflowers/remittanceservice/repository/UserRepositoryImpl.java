package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.User;
import io.dflowers.remittanceservice.entity.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        var entity = UserEntity.from(user);
        return jpaUserRepository.save(entity).toDomain();
    }

    @Override
    public void delete(long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public Optional<User> findOne(long id) {
        return jpaUserRepository.findById(id).map(UserEntity::toDomain);
    }
}
