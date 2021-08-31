package store.repositories;

import org.springframework.data.repository.CrudRepository;
import store.entities.Good;

import java.util.List;

public interface GoodRepository extends CrudRepository<Good, Long> {

    List<Good> findAll();
}
