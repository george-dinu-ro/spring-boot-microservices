package my.work.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import my.work.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	Optional<Product> findByCode(Integer code);

}
