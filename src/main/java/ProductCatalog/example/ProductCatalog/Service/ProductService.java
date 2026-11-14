package ProductCatalog.example.ProductCatalog.Service;

import ProductCatalog.example.ProductCatalog.Models.Product;
import ProductCatalog.example.ProductCatalog.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(long productId){
        Product p=new Product();
        p.setName("Iphone");
        p.setPrice(100000);
        return p;

    }


}
