package store.repositories;

import org.springframework.data.repository.CrudRepository;
import store.entities.Purchase;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

    @Override
    List<Purchase> findAll();
}
