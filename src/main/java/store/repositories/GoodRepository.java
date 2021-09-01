package store.repositories;

import org.springframework.data.repository.CrudRepository;
import store.entities.Good;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends CrudRepository<Good, Long> {

    @Override
    void deleteById(Long id);

    List<Good> findAll();

    @Override
    Optional<Good> findById(Long id);
}
