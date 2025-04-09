package se.yitingchang.testwebshop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yitingchang.testwebshop.Repository.ProductRepository;
import se.yitingchang.testwebshop.model.Product;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> search(String productName) {
        return productRepository.findByProductName(productName);
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }



    public Product saveProduct(Product product) {
        productRepository.save(product);
        return product;
    }



    public void deleteProductById(int productId) {
        Optional<Product> optional = productRepository.findById(productId);
        if (optional.isPresent()) {
            productRepository.delete(optional.get());
            System.out.println("Deleted product with ID: " + productId);
        } else {
            System.out.println("Product not found.");
        }
    }



    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword);
    }

}
