package my.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.work.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	boolean existsByCodeAndQuantityIsGreaterThanEqual(int code, int quantity);

}
