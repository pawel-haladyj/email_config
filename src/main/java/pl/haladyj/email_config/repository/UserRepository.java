package pl.haladyj.email_config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.haladyj.email_config.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByActivationKey(String activationKey);
}
