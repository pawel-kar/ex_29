package pl.pelikan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pelikan.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
