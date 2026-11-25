package ProductCatalog.example.ProductCatalog.Service.ProductService;

import ProductCatalog.example.ProductCatalog.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IProductService {

    Page<Product> getAllProduct(int page,int size,String sortBy,String sortDir);
    Product getProductById(long id);
    Product createProduct(Product product);

    Page<Product> search(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    boolean deleteProduct(Long id);

}
