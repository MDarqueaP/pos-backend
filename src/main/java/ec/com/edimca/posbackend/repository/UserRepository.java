package ec.com.edimca.posbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.edimca.posbackend.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
