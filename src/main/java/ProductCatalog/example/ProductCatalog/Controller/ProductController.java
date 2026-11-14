package ProductCatalog.example.ProductCatalog.Controller;

import ProductCatalog.example.ProductCatalog.Models.Product;
import ProductCatalog.example.ProductCatalog.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/products/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{Id}")
    public ResponseEntity<?> getProduct(@PathVariable("Id") long id){
        try {
            Product p=productService.getProduct(id);
            return ResponseEntity.status(200).body(p);
        }catch (Exception e){
            return ResponseEntity.status(500).body(Map.of("Message",e.getMessage()));
        }
    }
}
