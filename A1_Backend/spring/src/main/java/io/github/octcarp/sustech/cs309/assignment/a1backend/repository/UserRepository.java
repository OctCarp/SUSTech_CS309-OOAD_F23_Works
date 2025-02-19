package io.github.octcarp.sustech.cs309.assignment.a1backend.repository;

import io.github.octcarp.sustech.cs309.assignment.a1backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
