package by.eco_house.repository;

import by.eco_house.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for data management in a table "products"
 */
public interface ProductRepository extends JpaRepository<Product, Long>{

}
