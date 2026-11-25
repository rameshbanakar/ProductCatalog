package ProductCatalog.example.ProductCatalog.Repositories;

import ProductCatalog.example.ProductCatalog.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByPriceBetweenAndStatus(BigDecimal minPrice, BigDecimal maxPrice, String active, Pageable pageable);

    Page<Product> findByStatus(String active, Pageable pageable);
}
