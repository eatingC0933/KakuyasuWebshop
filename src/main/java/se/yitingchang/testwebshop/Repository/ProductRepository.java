package se.yitingchang.testwebshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import se.yitingchang.testwebshop.model.Category;
import se.yitingchang.testwebshop.model.Product;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {



    List<Product> findByCategoryCategoryId(int categoryId);
    Optional<Product> findById(int productId);
    Optional<Product> findByProductName(String productName);
    List<Product> findByProductNameContainingIgnoreCase(String keyword);


}
