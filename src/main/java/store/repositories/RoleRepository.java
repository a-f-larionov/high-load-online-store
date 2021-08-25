package store.repositories;

import org.springframework.data.repository.CrudRepository;
import store.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
