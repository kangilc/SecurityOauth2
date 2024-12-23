package net.javaf.SecurityOauth2.config.auth.dto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaf.SecurityOauth2.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
