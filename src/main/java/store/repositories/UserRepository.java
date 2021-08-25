package store.repositories;

import org.springframework.data.repository.CrudRepository;
import store.entities.User;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
