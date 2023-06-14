package ru.chernykh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernykh.backend.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);

}
