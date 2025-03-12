package ru.alex.manager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.alex.manager.entity.SelmagUser;

import java.util.Optional;

public interface SelmagUserRepository extends CrudRepository<SelmagUser, Integer> {
    Optional<SelmagUser> findByUsername(String username);
}
