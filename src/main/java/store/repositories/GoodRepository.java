package store.repositories;

import org.springframework.data.repository.CrudRepository;
import store.entities.Good;

public interface GoodRepository extends CrudRepository<Good, Long> {
}
