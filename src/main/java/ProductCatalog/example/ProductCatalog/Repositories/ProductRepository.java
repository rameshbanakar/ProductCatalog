package ProductCatalog.example.ProductCatalog.Repositories;

import ProductCatalog.example.ProductCatalog.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
